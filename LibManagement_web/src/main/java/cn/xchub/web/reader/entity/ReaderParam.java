package cn.xchub.web.reader.entity;

import lombok.Data;

@Data
public class ReaderParam {
    private Long currentPage;
    private Long pageSize;
    private String username;
    private String idCard;
    private String phone;
    private String learnNum;
}
