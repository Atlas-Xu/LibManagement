package cn.xchub.web.books.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.books.entity.BookVo;
import cn.xchub.web.books.entity.Books;
import cn.xchub.web.books.entity.BooksParm;
import cn.xchub.web.books.service.BooksService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    private BooksService booksService;

    //新增
    @Auth
    @PostMapping
    public ResultVo add(@RequestBody Books books) {
        boolean save = booksService.save(books);
        if (save){
            return ResultUtils.success("添加书籍成功！");
        }
        return ResultUtils.error("添加书籍失败！");
    }

    // 编辑
    @Auth
    @PutMapping
    public ResultVo edit(@RequestBody Books books) {
        boolean save = booksService.updateById(books);
        if (save){
            return ResultUtils.success("编辑书籍成功！");
        }
        return ResultUtils.error("编辑书籍失败！");
    }

    // 删除
    @Auth
    @DeleteMapping("/{bookId}")
    public ResultVo delete(@PathVariable("bookId") Long bookId){
        boolean remove = booksService.removeById(bookId);
        if (remove){
            return ResultUtils.success("删除书籍成功！");
        }
        return ResultUtils.error("删除书籍失败！");
    }

    //图书列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(BooksParm parm){
        IPage<Books> list = booksService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    //图书列表
    @Auth
    @GetMapping("/getHotBook")
    public ResultVo getHotBook(){
        List<BookVo> hotBook = booksService.getHotBook();
        return ResultUtils.success("查询成功",hotBook);
    }


}
