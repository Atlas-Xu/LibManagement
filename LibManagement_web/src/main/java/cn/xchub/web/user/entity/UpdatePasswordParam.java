package cn.xchub.web.user.entity;

import lombok.Data;

@Data
public class UpdatePasswordParam {
    private Long userId;
    private String oldPassword;
    private String password;
}
