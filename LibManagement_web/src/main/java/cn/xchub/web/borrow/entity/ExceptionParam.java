package cn.xchub.web.borrow.entity;

import lombok.Data;

@Data
public class ExceptionParam {
    private Long borrowId;
    private Long bookId;

    // 判断异常还是丢失
    private String type;
    private String exceptionText;
}
