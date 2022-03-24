package cn.xchub.web.books.service.impl;

import cn.xchub.web.books.entity.BookVo;
import cn.xchub.web.books.entity.Books;
import cn.xchub.web.books.entity.BooksParam;
import cn.xchub.web.books.mapper.BooksMapper;
import cn.xchub.web.books.service.BooksService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements BooksService {
    @Override
    public IPage<Books> getList(BooksParam param) {
        // 构造分页对象
        Page<Books> page = new Page<>();
        page.setCurrent(param.getCurrentPage());
        page.setSize(param.getPageSize());
        return this.baseMapper.getList(page, param);

    }

    @Override
    public int subBook(Long bookId) {
        final Books books = this.baseMapper.selectById(bookId);
        if (books == null) return 0;

        books.setBookStore(books.getBookStore() - 1);
        if (books.getBookStore() <= 0) return 0;

        return baseMapper.updateById(books);
    }

    @Override
    public int addBook(Long bookId) {
        final Books books = this.baseMapper.selectById(bookId);
        if (books == null) return 0;

        books.setBookStore(books.getBookStore() + 1);
        return baseMapper.updateById(books);
    }

    @Override
    public List<BookVo> getHotBook() {
        return this.baseMapper.getHotBook();
    }
}
