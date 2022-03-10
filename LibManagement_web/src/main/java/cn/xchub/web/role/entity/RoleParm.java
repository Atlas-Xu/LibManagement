package cn.xchub.web.role.entity;

import lombok.Data;

@Data
public class RoleParm {
    private Long currentPage;
    private Long pageSize;
    private String roleName;
}
