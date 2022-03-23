package cn.xchub.web.notice.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.notice.entity.Notice;
import cn.xchub.web.notice.entity.NoticeParam;
import cn.xchub.web.notice.service.NoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    // 新增
    @Auth
    @PostMapping
    public ResultVo add(@RequestBody Notice notice){
        boolean save = noticeService.save(notice);
        if (save){
           return ResultUtils.success("新增公告成功！");
        }
        return ResultUtils.error("新增公告失败！");
    }

    // 编辑
    @Auth
    @PutMapping
    public ResultVo edit(@RequestBody Notice notice){
        boolean save = noticeService.updateById(notice);
        if (save){
            return ResultUtils.success("编辑公告成功！");
        }
        return ResultUtils.error("编辑公告失败！");
    }

    // 删除
    @Auth
    @DeleteMapping("/{noticeId}")
    public ResultVo delete(@PathVariable("noticeId") Long noticeId ){
        boolean remove = noticeService.removeById(noticeId);
        if (remove){
            return ResultUtils.success("删除公告成功！");
        }
        return ResultUtils.error("删除公告失败！");
    }

    // 列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(NoticeParam param){
        IPage<Notice> list = noticeService.getList(param);
        return ResultUtils.success("查询成功", list);
    }

    // 首页公告列表
    @Auth
    @GetMapping("/getTopList")
    public ResultVo getTopList(){
        QueryWrapper<Notice> query = new QueryWrapper<>();
        query.lambda().orderByDesc(Notice::getCreateTime).last("limit 3");// 首页仅展示前三条
        List<Notice> list = noticeService.list(query);
        return ResultUtils.success("查询成功", list);
    }
}
