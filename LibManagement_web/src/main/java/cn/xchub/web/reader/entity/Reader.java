package cn.xchub.web.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 学号登录
 * */

@Data
@TableName("reader")
public class Reader {
    @TableId(type = IdType.AUTO)
    private Long readerId;
    private String learnNum;  //姓名
    private String username; //学号 由于Spring Security默认username和password这两个字段
    private String idCard;
    private String sex;
    private String phone;
    private String password;
    private String type;
    private String checkStatus;
    private String userStatus;
    private String className;

}
