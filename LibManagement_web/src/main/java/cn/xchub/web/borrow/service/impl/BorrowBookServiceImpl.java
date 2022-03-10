package cn.xchub.web.borrow.service.impl;

import cn.xchub.exception.BusinessException;
import cn.xchub.exception_advice.BusinessExceptionEnum;
import cn.xchub.web.books.entity.Books;
import cn.xchub.web.books.service.BooksService;
import cn.xchub.web.borrow.entity.*;
import cn.xchub.web.borrow.mapper.BorrowBookMapper;
import cn.xchub.web.borrow.service.BorrowBookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class BorrowBookServiceImpl extends ServiceImpl<BorrowBookMapper, BorrowBook> implements BorrowBookService {
    private Lock lock = new ReentrantLock();

    @Resource
    private BooksService booksService;

    @Override
    @Transactional
    public void borrow(BorrowParm parm, String userType) {
        // 加入事务注解，以便于不成功时回滚 @Transactional
        // 加锁
        lock.lock();
        try {
            // 查询图书库存是否充足
            // 构造查询条件
            QueryWrapper<Books> query = new QueryWrapper<>();
            query.lambda().in(Books::getBookId, parm.getBookIds());
            List<Books> list = booksService.list(query);
            // 查询库存是否充足
            List<Books> collect = list.stream().filter(item -> item.getBookStore().longValue() < 1L).collect(Collectors.toList());
            if (collect.size() > 0) {
                // 提示哪本图书库存不足
                List<String> stringList = collect.stream().map(Books::getBookName).collect(Collectors.toList());
                throw new BusinessException(BusinessExceptionEnum.NO_STOCK.getCode(), BusinessExceptionEnum.NO_STOCK.getMessage());
            }
            // 库存-1，插入借书明细
            List<Long> bookIds = parm.getBookIds();
            for (int i = 0; i < bookIds.size(); i++) {
                Long bookId = bookIds.get(i);
                int res = booksService.subBook(bookId);
                // 插入明细
                if (res > 0) {
                    BorrowBook borrowBook = new BorrowBook();
                    borrowBook.setBorrowId(bookId);
                    borrowBook.setReaderId(parm.getReaderId());
                    borrowBook.setReturnTime(parm.getReturnTime());
                    if (userType.equals("0")){ // 读者
                        borrowBook.setApplyStatus("0");
                        borrowBook.setBorrowStatus("0");
                    }else if (userType.equals("1")){ // 管理员
                        borrowBook.setApplyStatus("1");
                        borrowBook.setBorrowStatus("1");
                    }else {
                        throw new BusinessException(500,"用户类型不存在，无法借书");
                    }
                    borrowBook.setBorrowTime(new Date());
                    this.baseMapper.insert(borrowBook);
                }
            }

        } finally {
            // 释放锁
            lock.unlock();
        }

    }

    @Override
    public IPage<ReturnBook> getBorrowList(ListParm parm) {
        // 构造分页对象
        Page<ReturnBook> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getBorrowList(page,parm);
    }

    @Override
    @Transactional
    public void returnBook(List<ReturnParm> list) {
        // 加库存，变更借书状态
        for (int i = 0;i<list.size();i++){
            int res = booksService.addBook(list.get(i).getBookId());
            if (res > 0){
                BorrowBook borrowBook = new BorrowBook();
                borrowBook.setBorrowId(list.get(i).getBorrowId());
                borrowBook.setBorrowStatus("2");// 已还
                borrowBook.setReturnStatus("1");// 正常还书
                this.baseMapper.updateById(borrowBook);
            }
        }
    }

    @Override
    public void exceptionBook(ExceptionParm parm) {
        //0:异常、破损（可加库存）；1：丢失（不可加库存）
        String type = parm.getType();
        if (type.equals("0")){
            int res = booksService.addBook(parm.getBookId());
            if (res > 0){
                BorrowBook borrowBook = new BorrowBook();
                borrowBook.setBorrowId(parm.getBorrowId());
                borrowBook.setBorrowStatus("2");// 已还
                borrowBook.setReturnStatus("2");// 异常还书
                borrowBook.setExcepionText(parm.getExceptionText());
                this.baseMapper.updateById(borrowBook);
            }
        }else {
            // 丢失
            BorrowBook borrowBook = new BorrowBook();
            borrowBook.setBorrowId(parm.getBorrowId());
            borrowBook.setBorrowStatus("2");// 已还
            borrowBook.setReturnStatus("3");// 丢失
            borrowBook.setExcepionText(parm.getExceptionText());
            this.baseMapper.updateById(borrowBook);
        }
    }

    @Override
    public IPage<LookBorrow> getLookBorrowList(LookParm parm) {
        // 构造分页对象
        Page<LookBorrow> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getLookBorrowList(page,parm);
    }

    @Override
    public IPage<LookBorrow> getReaderLookBorrowList(LookParm parm) {
        // 构造分页对象
        Page<LookBorrow> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getReaderLookBorrowList(page, parm);
    }
}
