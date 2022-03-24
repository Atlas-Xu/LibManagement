package cn.xchub.web.borrow.entity;

import lombok.Data;

import java.util.List;

@Data
public class MachineBorrowParam {
    // 根据主键更新
    private Long borrowId;
    //借书人id
    private Long readerId;
    //图书id
    private List<Long> bookIds;
}
