package cn.xchub.web.borrow.entity;

import lombok.Data;

@Data
public class ReturnParm {
    private Long borrowId;
    private Long bookId;
}
