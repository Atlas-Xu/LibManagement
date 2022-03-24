package cn.xchub.web.borrow.service;

import cn.xchub.web.borrow.entity.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BorrowBookService extends IService<BorrowBook> {
    // 借书
    void borrow(BorrowParam param, String userType);
    // 还书列表
    IPage<ReturnBook> getBorrowList(ListParam param);
    // 还书
    void returnBook(List<ReturnParam> list);
    // 异常还书
    void exceptionBook(ExceptionParam param);
    // 借阅查看列表
    IPage<LookBorrow> getLookBorrowList(LookParam param);
    // 读者借阅查看列表
    IPage<LookBorrow> getReaderLookBorrowList(LookParam param);
    // 借阅机借书
    void borrowFromMachine(MachineBorrowParam param);
    // 借书机还书
    void returnFromMachine(MachineReturnParam param);
}
