package cn.xchub.web.books.entity;

import lombok.Data;

@Data
public class BooksParm {
    private Long currentPage;
    private Long pageSize;
    private String categoryId;
    private String bookName;
    private String bookPlaceNum;
    private String bookAuther;
}
