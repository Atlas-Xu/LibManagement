package cn.xchub.web.login.entity;

import lombok.Data;

@Data
public class LoginResult {
    private Long userId;
    private String token;
    //token过期时间
    private Long expireTime;
}
