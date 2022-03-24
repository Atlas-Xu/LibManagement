package cn.xchub.web.books.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.books.entity.BookVo;
import cn.xchub.web.books.entity.Books;
import cn.xchub.web.books.entity.BooksParam;
import cn.xchub.web.books.service.BooksService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    /**
     * 新增
     */
    @Auth
    @PostMapping
    public ResultVo<Object> add(@RequestBody Books books) {
        boolean save = booksService.save(books);
        if (save) {
            return ResultUtils.success("添加书籍成功！");
        }
        return ResultUtils.error("添加书籍失败！");
    }

    /**
     * 编辑
     */
    @Auth
    @PutMapping
    public ResultVo<Object> edit(@RequestBody Books books) {
        boolean save = booksService.updateById(books);
        if (save) {
            return ResultUtils.success("编辑书籍成功！");
        }
        return ResultUtils.error("编辑书籍失败！");
    }

    /**
     * 删除
     */
    @Auth
    @DeleteMapping("/{bookId}")
    public ResultVo<Object> delete(@PathVariable("bookId") Long bookId) {
        boolean remove = booksService.removeById(bookId);
        if (remove) {
            return ResultUtils.success("删除书籍成功！");
        }
        return ResultUtils.error("删除书籍失败！");
    }

    /**
     * 图书列表
     */
    @Auth
    @GetMapping("/list")
    public ResultVo<Object> getList(BooksParam param) {
        IPage<Books> list = booksService.getList(param);
        return ResultUtils.success("查询成功", list);
    }

    /**
     * 图书列表
     */
    @Auth
    @GetMapping("/getHotBook")
    public ResultVo<Object> getHotBook() {
        List<BookVo> hotBook = booksService.getHotBook();
        return ResultUtils.success("查询成功", hotBook);
    }

    // TODO 根据RFID信息获得图书信息，传回图书名称 图书id和图书作者
}
