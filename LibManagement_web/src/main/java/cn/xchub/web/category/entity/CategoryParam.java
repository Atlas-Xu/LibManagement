package cn.xchub.web.category.entity;

import lombok.Data;

@Data
public class CategoryParam {
    private Long currentPage;
    private Long pageSize;
    private String categoryName;
}
