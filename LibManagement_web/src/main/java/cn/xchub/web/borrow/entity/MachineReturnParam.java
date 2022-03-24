package cn.xchub.web.borrow.entity;

import lombok.Data;

import java.util.List;

@Data
public class MachineReturnParam {
    Long readerId;
    //图书id
    private List<Long> bookIds;
}
