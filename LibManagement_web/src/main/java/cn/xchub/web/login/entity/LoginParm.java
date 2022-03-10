package cn.xchub.web.login.entity;

import lombok.Data;

@Data
public class LoginParm {
    private String username;
    private String password;
    private String userType;
}
