package cn.xchub.rfid.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetBookResponse {
    private Long bookId;
    private Long categoryId;
    private String categoryName;
    private String bookName;
    private String bookCode;
    private String bookPlaceNum;
    private String bookAuthor;
    private String bookProduct;
    private BigDecimal bookPrice;
    private Integer bookStore;
}

