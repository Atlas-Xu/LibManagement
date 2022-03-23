package cn.xchub.web.login.entity;

import lombok.Data;

@Data
public class LoginParam {
    private String username;
    private String password;
    private String userType;
}
