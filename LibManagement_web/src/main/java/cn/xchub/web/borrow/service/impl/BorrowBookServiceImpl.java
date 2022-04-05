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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
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
    @Autowired
    private BorrowBookService borrowBookService;

    @Override
    @Transactional
    public void borrow(BorrowParam param, String userType) {
        // 加入事务注解，以便于不成功时回滚 @Transactional
        // 加锁
        lock.lock();
        try {
            // 查询图书库存是否充足
            // 构造查询条件
            QueryWrapper<Books> query = new QueryWrapper<>();
            query.lambda().in(Books::getBookId, param.getBookIds());
            List<Books> list = booksService.list(query);
            // 查询库存是否充足
            List<Books> collect = list.stream().filter(item -> item.getBookStore().longValue() < 1L).collect(Collectors.toList());
            if (collect.size() > 0) {
                // 提示哪本图书库存不足
                List<String> stringList = collect.stream().map(Books::getBookName).collect(Collectors.toList());
                throw new BusinessException(BusinessExceptionEnum.NO_STOCK.getCode(), BusinessExceptionEnum.NO_STOCK.getMessage());
            }
            // 库存-1，插入借书明细
            List<Long> bookIds = param.getBookIds();
            for (int i = 0; i < bookIds.size(); i++) {
                Long bookId = bookIds.get(i);
                int res = booksService.subBook(bookId);
                // 插入明细
                if (res > 0) {
                    BorrowBook borrowBook = new BorrowBook();
                    borrowBook.setBookId(bookId);
                    borrowBook.setReaderId(param.getReaderId());
                    borrowBook.setReturnTime(param.getReturnTime());
                    if (userType.equals("0")) { // 读者
                        borrowBook.setApplyStatus("0");
                        borrowBook.setBorrowStatus("0");
                    } else if (userType.equals("1")) { // 管理员
                        borrowBook.setApplyStatus("1");
                        borrowBook.setBorrowStatus("1");
                    } else {
                        throw new BusinessException(500, "用户类型不存在，无法借书");
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
    public IPage<ReturnBook> getBorrowList(ListParam param) {
        // 构造分页对象
        Page<ReturnBook> page = new Page<>();
        page.setCurrent(param.getCurrentPage());
        page.setSize(param.getPageSize());
        return this.baseMapper.getBorrowList(page, param);
    }

    @Override
    @Transactional
    public void returnBook(List<ReturnParam> list) {
        list.forEach(it -> {
            final BorrowBook borrowBook = baseMapper.selectById(it.getBorrowId());
            if (borrowBook == null) return;
            booksService.addBook(it.getBookId());
            borrowBook.setBorrowStatus("2");// 已还
            borrowBook.setReturnStatus("1");// 正常还书
            baseMapper.updateById(borrowBook);
        });
    }

    @Override
    public void exceptionBook(ExceptionParam param) {
        //0:异常、破损（可加库存）；1：丢失（不可加库存）
        String type = param.getType();
        if (type.equals("0")) {
            int res = booksService.addBook(param.getBookId());
            if (res > 0) {
                BorrowBook borrowBook = new BorrowBook();
                borrowBook.setBorrowId(param.getBorrowId());
                borrowBook.setBorrowStatus("2");// 已还
                borrowBook.setReturnStatus("2");// 异常还书
                borrowBook.setExcepionText(param.getExceptionText());
                this.baseMapper.updateById(borrowBook);
            }
        } else {
            // 丢失
            BorrowBook borrowBook = new BorrowBook();
            borrowBook.setBorrowId(param.getBorrowId());
            borrowBook.setBorrowStatus("2");// 已还
            borrowBook.setReturnStatus("3");// 丢失
            borrowBook.setExcepionText(param.getExceptionText());
            this.baseMapper.updateById(borrowBook);
        }
    }

    @Override
    public IPage<LookBorrow> getLookBorrowList(LookParam param) {
        // 构造分页对象
        Page<LookBorrow> page = new Page<>();
        page.setCurrent(param.getCurrentPage());
        page.setSize(param.getPageSize());
        return this.baseMapper.getLookBorrowList(page, param);
    }

    @Override
    public IPage<LookBorrow> getReaderLookBorrowList(LookParam param) {
        // 构造分页对象
        Page<LookBorrow> page = new Page<>();
        page.setCurrent(param.getCurrentPage());
        page.setSize(param.getPageSize());
        return this.baseMapper.getReaderLookBorrowList(page, param);
    }

    @Override
    @Transactional
    public void borrowFromMachine(MachineBorrowParam param) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.add(Calendar.DATE, 30);//借书机自动设置30天到期
        List<Long> bookIds = param.getBookIds();
        for (int i = 0; i < bookIds.size(); i++) {
            Long bookId = bookIds.get(i);
            BorrowBook borrowBook = new BorrowBook();
            borrowBook.setBookId(bookId);
            borrowBook.setReaderId(param.getReaderId());
            borrowBook.setReturnTime(new Date());
            borrowBook.setApplyStatus("1");
            borrowBook.setBorrowStatus("1");
            borrowBook.setBorrowTime(now.getTime());
            this.baseMapper.insert(borrowBook);
        }
    }

    @Override
    @Transactional
    public void returnFromMachine(MachineReturnParam param) {
        List<Long> bookIds = param.getBookIds();
        for (int i = 0; i < bookIds.size(); i++) {
            int res = booksService.addBook(bookIds.get(i));
            if (res > 0) {
                QueryWrapper<BorrowBook> query = new QueryWrapper<>();
                query.lambda().eq(BorrowBook::getReaderId, param.getReaderId()).eq(BorrowBook::getBookId, bookIds.get(i));
                BorrowBook one = borrowBookService.getOne(query);
                one.setBorrowStatus("2");// 已还
                one.setReturnStatus("1");// 正常还书
                this.baseMapper.updateById(one);
            }
        }
    }

}
