package cn.xchub.web.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {
    // 主键，自动递增
    @TableId(type = IdType.AUTO)
    private Long userId;
    //排除该字段
    @TableField(exist = false)
    private Long roleId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String sex;
    private String isAdmin;
    private boolean isAccountNonExpired = true;//帐户是否过期(1 未过期，0已过期)
    private boolean isAccountNonLocked = true;//帐户是否被锁定(1 未锁定，0已锁定)
    private boolean isCredentialsNonExpired = true;//密码是否过期(1 未过期，0已过期)
    private boolean isEnabled = true;//帐户是否可用(1 可用，0 删除用户)
    private String nickName;
    private Date createTime;
    private Date updateTime;
}
