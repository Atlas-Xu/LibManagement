package cn.xchub.web.notice.entity;

import lombok.Data;

@Data
public class NoticeParm {
    private Long currentPage;
    private Long pageSize;
    private String noticeTitle;
}
