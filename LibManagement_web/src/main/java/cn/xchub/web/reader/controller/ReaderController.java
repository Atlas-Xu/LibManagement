package cn.xchub.web.reader.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.reader.entity.Reader;
import cn.xchub.web.reader.entity.ReaderParm;
import cn.xchub.web.reader.service.ReaderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/reader")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    // 增
    @Auth
    @PostMapping
    public ResultVo addReader(@RequestBody Reader reader){
        //查询学号是否已被占用（防止重复）
        QueryWrapper<Reader> query = new QueryWrapper<>();
        query.lambda().eq(Reader::getUsername,reader.getUsername());
        Reader one = readerService.getOne(query);
        if (one != null){
            return ResultUtils.error("学号已被占用！");
        }
        reader.setPassword(DigestUtils.md5DigestAsHex(reader.getPassword().getBytes()));
        //默认已审核
        reader.setCheckStatus("1");
        reader.setUserStatus("1");
        readerService.saveReader(reader);
        return ResultUtils.success("新增用户成功！");

    }

    // 编辑
    @Auth
    @PutMapping
    public ResultVo editReader(@RequestBody Reader reader){
        readerService.editReader(reader);
        return ResultUtils.success("编辑用户成功！");
    }

    // 删除
    @Auth
    @DeleteMapping("/{readerId}")
    public ResultVo deleteReader(@PathVariable("readerId") Long readerId){
        boolean remove = readerService.removeById(readerId);
        if(remove){
            return ResultUtils.success("删除用户成功！");
        }
        return ResultUtils.error("删除用户失败！");
    }

    // 列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(ReaderParm parm){
        IPage<Reader> list = readerService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    // 根据学号查询
    @Auth
    @GetMapping("/getByUserName")
    public ResultVo getByUsername(Reader reader){
        QueryWrapper<Reader> query = new QueryWrapper<>();
        query.lambda().eq(Reader::getUsername,reader.getUsername());
        Reader one = readerService.getOne(query);
        return ResultUtils.success("查询成功！", one);
    }

    // 读者注册
    @PostMapping("/register")
    public ResultVo register(@RequestBody Reader reader){
        //查询学号是否已被占用（防止重复）
        QueryWrapper<Reader> query = new QueryWrapper<>();
        query.lambda().eq(Reader::getUsername,reader.getUsername());
        Reader one = readerService.getOne(query);
        if (one != null){
            return ResultUtils.error("学号已被占用！");
        }
        reader.setPassword(DigestUtils.md5DigestAsHex(reader.getPassword().getBytes()));
        //默认未审核
        reader.setCheckStatus("0");
        reader.setUserStatus("1");
        readerService.saveReader(reader);
        return ResultUtils.success("注册成功！");
    }

    // 审核
    @PutMapping("/applyReader")
    public ResultVo applyReader(@RequestBody Reader reader){
        reader.setCheckStatus("1");
        readerService.updateById(reader);
        return ResultUtils.success("编辑用户成功！");
    }

    // 读者总数
    @Auth
    @GetMapping("/getReaderCount")
    public ResultVo getReaderCount(){
        int count = readerService.count();
        return ResultUtils.success("查询成功",count);
    }

    // 待审核读者总数
    @Auth
    @GetMapping("/getApplyReaderCount")
    public ResultVo getApplyReaderCount(){
        QueryWrapper<Reader> query = new QueryWrapper<>();
        query.lambda().eq(Reader::getCheckStatus,"0");
        int count = readerService.count(query);
        return ResultUtils.success("查询成功",count);
    }

    // 重置密码
    @PostMapping("/resetPassword")
    public ResultVo resetPassword(@RequestBody Reader reader){
        String password = "123456";
        reader.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        boolean b = readerService.updateById(reader);
        if (b){
            return ResultUtils.success("重置密码成功");
        }
        return ResultUtils.error("重置密码失败");
    }
}
