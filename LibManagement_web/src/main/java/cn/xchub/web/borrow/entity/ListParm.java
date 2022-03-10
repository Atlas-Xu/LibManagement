package cn.xchub.web.borrow.entity;

import lombok.Data;

@Data
public class ListParm {
    private Long currentPage;
    private Long pageSize;
    private String username;
    private String borrowStatus;
}
