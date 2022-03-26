package cn.xchub.web.books.service;

import cn.xchub.web.books.entity.BookVo;
import cn.xchub.web.books.entity.Books;
import cn.xchub.web.books.entity.BooksParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BooksService extends IService<Books> {
    IPage<Books> getList(BooksParam param);

    /**
     * 减库存（借书）
     */
    int subBook(Long bookId);

    /**
     * 加库存（还书）
     */
    int addBook(Long bookId);

    /**
     * 查找最热门的10本
     */
    List<BookVo> getHotBook();

    Books getByBookCode(String bookCode);
}
