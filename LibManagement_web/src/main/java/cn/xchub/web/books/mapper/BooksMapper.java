package cn.xchub.web.books.mapper;

import cn.xchub.web.books.entity.BookVo;
import cn.xchub.web.books.entity.Books;
import cn.xchub.web.books.entity.BooksParm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BooksMapper extends BaseMapper<Books> {
    IPage<Books> getList(Page<Books> page, @Param("parm") BooksParm parm); // parm别名用于mapper.xml中的SQL语句别名
    int subBook(@Param("bookId") Long bookId);
    int addBook(@Param("bookId") Long bookId);
    List<BookVo> getHotBook();
}
