package cn.xchub.web.borrow.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.jwt.JwtUtils;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.books.entity.BooksParam;
import cn.xchub.web.borrow.entity.*;
import cn.xchub.web.borrow.service.BorrowBookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowBookController {
    @Autowired
    private BorrowBookService borrowBookService;

    @Autowired
    private JwtUtils jwtUtils;

    @Auth
    @PostMapping("")
    public ResultVo borrow(@RequestBody BorrowParam param, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            return ResultUtils.error("token过期！",600);
        }
        Claims claims = jwtUtils.getClaimsFromToken(token);
        String userType = (String) claims.get("userType");
        borrowBookService.borrow(param,userType);
        return ResultUtils.success("借书成功！");
    }

    // 还书列表
    @Auth
    @GetMapping("/getBorrowList")
    public ResultVo getBorrowList(ListParam param) {
        IPage<ReturnBook> borrowList = borrowBookService.getBorrowList(param);
        return ResultUtils.success("查询成功！", borrowList);
    }

    // 还书
    @Auth
    @PostMapping("/returnBooks")
    public ResultVo returnBooks(@RequestBody List<ReturnParam> param) {
        borrowBookService.returnBook(param);
        // TODO 还书功能虽然运行好像没问题，但是显示的内容还是还书不成功
        return ResultUtils.success("还书成功！");
    }

    // 异常还书
    @Auth
    @PostMapping("/exceptionBooks")
    public ResultVo exceptionBooks(@RequestBody ExceptionParam param) {
        borrowBookService.exceptionBook(param);
        return ResultUtils.success("还书成功！");
    }

    // 借阅查看
    @Auth
    @GetMapping("/getLookBorrowList")
    public ResultVo getLookBorrowList(LookParam param, HttpServletRequest request) {
        // 获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            return ResultUtils.error("token过期！",600);
        }
        Claims claims = jwtUtils.getClaimsFromToken(token);
        String userType = (String) claims.get("userType");
        IPage<LookBorrow> lookBorrowList = null;
        if (userType.equals("0")){ // 读者
            lookBorrowList = borrowBookService.getReaderLookBorrowList(param);
            return ResultUtils.success("查询成功！", lookBorrowList);
            // TODO 实际上读者查看不到自己的
        }else if (userType.equals("1")){ // 管理员
            lookBorrowList = borrowBookService.getLookBorrowList(param);
            return ResultUtils.success("查询成功！", lookBorrowList);
        }else {
            return ResultUtils.success("查询成功！", lookBorrowList);
        }
    }

    // 借书审核
    @Auth
    @PostMapping("/applyBook")
    public ResultVo applyBook(@RequestBody BorrowBook borrowBook){
        borrowBook.setBorrowStatus("1");
        borrowBook.setApplyStatus("1");
        boolean b = borrowBookService.updateById(borrowBook);
        if (b){
            return ResultUtils.success("审核成功");
        }
        return ResultUtils.error("审核失败");
    }

    // 借书续期
    @Auth
    @PostMapping("/addTime")
    public ResultVo addTime(@RequestBody BorrowParam param){
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setBorrowId(param.getBorrowId());
        borrowBook.setReturnTime(param.getReturnTime());
        boolean b = borrowBookService.updateById(borrowBook);
        if (b){
            return ResultUtils.success("续借成功！");
        }
        return ResultUtils.error("续借失败！");
    }

    // 待审核预借总数
    @Auth
    @GetMapping("/getBorrowApplyCount")
    public ResultVo getBorrowApplyCount(String userType,Long userId){
        if (userType.equals("0")){ // 读者
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getApplyStatus,"0").eq(BorrowBook::getReaderId,userId);
            int count = borrowBookService.count();
            return ResultUtils.success("查询成功",count);
        }else if (userType.equals("1")){ // 管理员
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getApplyStatus,"0");
            int count = borrowBookService.count();
            return ResultUtils.success("查询成功",count);
        }else {
            return ResultUtils.success("查询成功",0);
        }

    }

    // 到期待还总数
    @Auth
    @GetMapping("/getBorrowReturnCount")
    public ResultVo getBorrowReturnCount(String userType,Long userId){
        if (userType.equals("0")){ // 读者
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getBorrowStatus,"1").lt(BorrowBook::getReturnTime,new Date()).eq(BorrowBook::getReaderId,userId);
            int count = borrowBookService.count();
            return ResultUtils.success("查询成功",count);
        }else if (userType.equals("1")){ // 管理员
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getBorrowStatus,"1").lt(BorrowBook::getReturnTime,new Date());
            int count = borrowBookService.count();
            return ResultUtils.success("查询成功",count);
        }else {
            return ResultUtils.success("查询成功",0);
        }
    }


    @PostMapping("/borrowFromMachine")
    public ResultVo borrowFromMachine(@RequestBody MachineBorrowParam param){
        borrowBookService.borrowFromMachine(param);
        return ResultUtils.success("借阅成功");
    }

    @PostMapping("/returnFromMachine")
    public ResultVo returnFromMachine(@RequestBody MachineReturnParam param){
        borrowBookService.returnFromMachine(param);
        return ResultUtils.success("还书成功");
    }
}
