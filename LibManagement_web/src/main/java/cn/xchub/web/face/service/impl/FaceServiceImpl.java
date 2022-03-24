package cn.xchub.web.face.service.impl;

import cn.xchub.exception.BusinessException;
import cn.xchub.exception_advice.BusinessExceptionEnum;
import cn.xchub.utils.Base64Util;
import cn.xchub.web.face.entity.Face;
import cn.xchub.web.face.entity.FaceLoginParam;
import cn.xchub.web.face.entity.FaceParam;
import cn.xchub.web.face.factory.FaceEngineFactory;
import cn.xchub.web.face.mapper.FaceMapper;
import cn.xchub.web.face.service.FaceService;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FaceServiceImpl extends ServiceImpl<FaceMapper, Face> implements FaceService {

    @Value("${config.arcface-sdk.sdk-lib-path}")
    public String sdkLibPath;

    @Value("${config.arcface-sdk.app-id}")
    public String appId;

    @Value("${config.arcface-sdk.sdk-key}")
    public String sdkKey;

    @Value("${config.arcface-sdk.detect-pool-size}")
    public Integer detectPooSize;

    @Value("${config.arcface-sdk.compare-pool-size}")
    public Integer comparePooSize;

    private ExecutorService compareExecutorService;

    //通用人脸识别引擎池
    private GenericObjectPool<FaceEngine> faceEngineGeneralPool;

    //人脸比对引擎池
    private GenericObjectPool<FaceEngine> faceEngineComparePool;


    @Override
    @Transactional(readOnly = true)
    public Map<byte[], Face> faceFeatures() {
        return baseMapper.selectList(Wrappers.lambdaQuery(Face.class))
                .stream()
                .collect(Collectors.toMap(Face::getFaceFeature, Function.identity()));
    }


    @PostConstruct
    public void init() {
        // 录入
        GenericObjectPoolConfig detectPoolConfig = new GenericObjectPoolConfig();
        detectPoolConfig.setMaxIdle(detectPooSize);
        detectPoolConfig.setMaxTotal(detectPooSize);
        detectPoolConfig.setMinIdle(detectPooSize);
        detectPoolConfig.setLifo(false);
        EngineConfiguration detectCfg = new EngineConfiguration();
        FunctionConfiguration detectFunctionCfg = new FunctionConfiguration();
        detectFunctionCfg.setSupportFaceDetect(true);//开启人脸检测功能
        detectFunctionCfg.setSupportFaceRecognition(true);//开启人脸识别功能
        detectFunctionCfg.setSupportLiveness(true);//开启活体检测功能
        detectCfg.setFunctionConfiguration(detectFunctionCfg);
        detectCfg.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);//图片检测模式，用于注册人脸库
        detectCfg.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);//人脸旋转角度
        faceEngineGeneralPool = new GenericObjectPool(new FaceEngineFactory(sdkLibPath, appId, sdkKey, null, detectCfg), detectPoolConfig);//底层库算法对象池

        // 比较
        GenericObjectPoolConfig comparePoolConfig = new GenericObjectPoolConfig();
        comparePoolConfig.setMaxIdle(comparePooSize);
        comparePoolConfig.setMaxTotal(comparePooSize);
        comparePoolConfig.setMinIdle(comparePooSize);
        comparePoolConfig.setLifo(false);
        EngineConfiguration compareCfg = new EngineConfiguration();
        FunctionConfiguration compareFunctionCfg = new FunctionConfiguration();
        compareFunctionCfg.setSupportFaceRecognition(true);//开启人脸识别功能
        compareFunctionCfg.setSupportLiveness(true);//开启活体检测功能
        compareCfg.setFunctionConfiguration(compareFunctionCfg);
        compareCfg.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);//图片检测模式，用于登录
        compareCfg.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);//人脸旋转角度
        faceEngineComparePool = new GenericObjectPool(new FaceEngineFactory(sdkLibPath, appId, sdkKey, null, compareCfg), comparePoolConfig);//底层库算法对象池
        compareExecutorService = Executors.newFixedThreadPool(comparePooSize);
    }


    /**
     * 检测传入的图片中的人脸
     * */
    @Override
    public List<FaceInfo> detectFaces(ImageInfo imageInfo) {
         FaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new BusinessException(BusinessExceptionEnum.FAIL.getCode(), "获取引擎失败");
            }
            //人脸检测得到人脸列表
            List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
            int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
            if (errorCode == 0) {
                return faceInfoList;
            } else {
                log.error("特征提取失败" + errorCode);
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (faceEngine != null) {
                //释放引擎对象
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
        return null;
    }

    /**
     * 在faceLogin接口中使用。
     * */
    @Override
    public Long compareFace(FaceLoginParam param) {
        // TODO 人脸比对，从数据库查询符合阈值且最高的那个，返回读者id
        ImageInfo imageInfo = ImageFactory.getRGBData(Base64Util.base64ToBytes(param.getBase64Str()));
        List<FaceInfo> faceInfoList = detectFaces(imageInfo);
        byte[] targetFeature = extractFaceFeature(imageInfo, faceInfoList.get(0));
        if (targetFeature == null) {
            return null;
        }
        // 还差调用sdk进行阈值比较，返回在数据库中能匹配到的阈值符合的对象，若无则直接返回null
        return null;
    }

    /**
     * 提取人脸特征
     * */
    @Override
    public byte[] extractFaceFeature(ImageInfo imageInfo, FaceInfo faceInfo) {
        FaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new BusinessException(BusinessExceptionEnum.FAIL.getCode(), "获取引擎失败");
            }
            FaceFeature faceFeature = new FaceFeature();
            // 提取人脸特征
            int errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfo, faceFeature);
            // TODO 防止人脸重复（有空再做）
            if (errorCode == 0) {
                return faceFeature.getFeatureData();
            } else {
                log.error("特征提取失败，errorCode：" + errorCode);
                return null;
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean addFace(FaceParam param) {
        Face face = new Face();
        ImageInfo imageInfo = ImageFactory.getRGBData(Base64Util.base64ToBytes(param.getBase64Str()));
        List<FaceInfo> faceInfoList = detectFaces(imageInfo);
        byte[] faceFeature = extractFaceFeature(imageInfo, faceInfoList.get(0));
        if (faceFeature == null) {
            return false;
        }
        face.setFaceFeature(faceFeature);
        face.setReaderId(param.getReaderId());
        face.setUpdateTime(new Date());
        this.baseMapper.insert(face);
        return true;

    }


}
