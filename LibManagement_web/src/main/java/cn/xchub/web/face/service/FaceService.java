package cn.xchub.web.face.service;


import cn.xchub.web.face.entity.Face;
import cn.xchub.web.face.entity.FaceParam;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FaceService extends IService<Face> {
    byte[] extractFaceFeature(ImageInfo imageInfo, FaceInfo faceInfo);
    boolean addFace(FaceParam param);
    List<FaceInfo> detectFaces(ImageInfo imageInfo);
    boolean compareFace();
}
