package cn.xchub.web.borrow.mapper;

import cn.xchub.web.borrow.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface BorrowBookMapper extends BaseMapper<BorrowBook> {
    IPage<ReturnBook> getBorrowList(Page<ReturnBook> page, @Param("parm") ListParm parm);
    // 管理员借阅查看列表
    IPage<LookBorrow> getLookBorrowList(Page<LookBorrow> page, @Param("parm")LookParm parm);
    // 读者借阅查看列表
    IPage<LookBorrow> getReaderLookBorrowList(Page<LookBorrow> page, @Param("parm")LookParm parm);
}
