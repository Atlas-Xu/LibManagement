package cn.xchub.web.borrow.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 根据前端需要的传值定义
 * */

@Data
public class BorrowParam {
    // 根据主键更新
    private Long borrowId;
    //借书人id
    private Long readerId;
    //图书id
    private List<Long> bookIds;
    //还书时间
    private Date returnTime;
}
