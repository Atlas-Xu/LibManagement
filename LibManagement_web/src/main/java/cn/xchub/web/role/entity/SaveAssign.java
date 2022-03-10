package cn.xchub.web.role.entity;

import lombok.Data;

import java.util.List;

@Data
public class SaveAssign {
    private Long roleId;
    private List<Long> list;
}
