package cn.xchub.web.notice.entity;

import lombok.Data;

@Data
public class NoticeParam {
    private Long currentPage;
    private Long pageSize;
    private String noticeTitle;
}
