package cn.xchub.web.face.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("face")
public class Face {
    @TableId(type = IdType.AUTO)
    private Long faceId;
    private byte[] faceFeature;
    private Long readerId;
    private Date updateTime;
}
