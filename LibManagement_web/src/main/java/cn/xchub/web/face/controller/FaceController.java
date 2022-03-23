package cn.xchub.web.face.controller;


import cn.xchub.annotation.Auth;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.face.entity.FaceParam;
import cn.xchub.web.face.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/face")
public class FaceController {
    @Autowired
    FaceService faceService;

    // 新增
    @Auth
    @PostMapping()
    public ResultVo addFace(@RequestBody FaceParam param){
        boolean b = faceService.addFace(param);
        if (b){
            return ResultUtils.success("人脸信息添加成功");
        }else {
            return ResultUtils.error("请传入清晰的人脸照片");
        }
    }
}
