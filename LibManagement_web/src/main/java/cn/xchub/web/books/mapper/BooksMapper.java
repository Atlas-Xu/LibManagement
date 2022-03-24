package cn.xchub.web.books.mapper;

import cn.xchub.web.books.entity.BookVo;
import cn.xchub.web.books.entity.Books;
import cn.xchub.web.books.entity.BooksParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BooksMapper extends BaseMapper<Books> {
    IPage<Books> getList(Page<Books> page, @Param("param") BooksParam param); // param别名用于mapper.xml中的SQL语句别名

    List<BookVo> getHotBook();
}
