package cn.xchub.web.borrow.entity;

import lombok.Data;

@Data
public class ReturnParam {
    private Long borrowId;
    private Long bookId;
}
