package cn.xchub.web.notice.service.impl;

import cn.xchub.web.notice.entity.Notice;
import cn.xchub.web.notice.entity.NoticeParm;
import cn.xchub.web.notice.mapper.NoticeMapper;
import cn.xchub.web.notice.service.NoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Override
    public IPage<Notice> getList(NoticeParm parm) {
        // 构造分页对象
        Page<Notice> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(page.getCurrent());
        // 构造查询条件
        QueryWrapper<Notice> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getNoticeTitle())){
            query.lambda().like(Notice::getNoticeTitle,parm.getNoticeTitle());
        }
        return this.baseMapper.selectPage(page,query);
    }
}
