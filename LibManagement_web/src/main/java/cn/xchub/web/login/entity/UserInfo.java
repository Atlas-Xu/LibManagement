package cn.xchub.web.login.entity;

import lombok.Data;

@Data
public class UserInfo {
    private String name;
    private String avatar;
    private String introduction;
    private Object[] roles;
}
