package cn.xchub.web.books.service.impl;

import cn.xchub.web.books.entity.BookVo;
import cn.xchub.web.books.entity.Books;
import cn.xchub.web.books.entity.BooksParm;
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
    public IPage<Books> getList(BooksParm parm) {
        // 构造分页对象
        Page<Books> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getList(page,parm);

    }

    @Override
    public int subBook(Long bookId) {
        return this.baseMapper.subBook(bookId);
    }

    @Override
    public int addBook(Long bookId) {
        return this.baseMapper.addBook(bookId);
    }

    @Override
    public List<BookVo> getHotBook() {
        return this.baseMapper.getHotBook();
    }
}
