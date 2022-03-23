package cn.xchub.web.books.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("books")
public class Books {
    @TableId(type = IdType.AUTO)
    private Long bookId;
    private Long categoryId;
    @TableField(exist = false)
    private String categoryName;
    private String bookName;
    private String bookCode;
    private String bookPlaceNum;
    private String bookAuthor;
    private String bookProduct;
    private BigDecimal bookPrice;
    private Integer bookStore;
}
