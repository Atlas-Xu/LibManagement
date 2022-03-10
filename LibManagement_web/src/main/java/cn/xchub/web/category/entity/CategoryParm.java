package cn.xchub.web.category.entity;

import lombok.Data;

@Data
public class CategoryParm {
    private Long currentPage;
    private Long pageSize;
    private String categoryName;
}
