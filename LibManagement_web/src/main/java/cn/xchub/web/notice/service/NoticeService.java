package cn.xchub.web.notice.service;

import cn.xchub.web.notice.entity.Notice;
import cn.xchub.web.notice.entity.NoticeParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoticeService extends IService<Notice> {
    IPage<Notice> getList(NoticeParam param);
}
