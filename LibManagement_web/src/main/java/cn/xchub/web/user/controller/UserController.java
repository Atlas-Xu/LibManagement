package cn.xchub.web.user.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.jwt.JwtUtils;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.reader.entity.Reader;
import cn.xchub.web.reader.service.ReaderService;
import cn.xchub.web.role.entity.Role;
import cn.xchub.web.role.service.RoleService;
import cn.xchub.web.user.entity.PageParm;
import cn.xchub.web.user.entity.UpdatePasswordParm;
import cn.xchub.web.user.entity.User;
import cn.xchub.web.user.service.UserService;
import cn.xchub.web.user_role.entity.UserRole;
import cn.xchub.web.user_role.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ReaderService readerService;

    //新增用户
    @Auth
    @PostMapping
    public ResultVo addUser(@RequestBody User user) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername, user.getUsername());
        User one = userService.getOne(query);
        if (one != null) {
            return ResultUtils.error("账户被占用！");
        }
        //MD5加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        user.setIsAdmin("0");
        user.setCreateTime(new Date());

        userService.addUser(user);
        return ResultUtils.success("新增用户成功！");

    }

    //编辑用户
    @Auth
    @PutMapping
    public ResultVo editUser(@RequestBody User user) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername, user.getUsername());
        User one = userService.getOne(query);
        if (one != null && one.getUserId() != user.getUserId()) {
            return ResultUtils.error("账户被占用！");
        }
        //MD5加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setUpdateTime(new Date());

        userService.editUser(user);
        return ResultUtils.success("编辑用户成功！");

    }

    //删除用户
    @Auth
    @DeleteMapping("/{userid}")
    public ResultVo delete(@PathVariable("userId") Long userid) {
        boolean remove = userService.removeById(userid);
        if (remove) {
            return ResultUtils.success("删除用户成功！");
        } else {
            return ResultUtils.success("删除用户失败！");
        }
    }

    //列表查询
    @Auth
    @GetMapping("/list")
    public ResultVo getList(PageParm parm) {
        IPage<User> list = userService.list(parm);
        //密码处理
        list.getRecords().stream().forEach(item -> {
            item.setPassword("");
        });
        return ResultUtils.success("查询成功", list);
    }

    // 查询角色列表
    @Auth
    @GetMapping("/getRoleList")
    public ResultVo getRoleList() {
        List<Role> list = roleService.list();
        return ResultUtils.success("查询成功", list);
    }

    // 根据用户id查询角色
    @Auth
    @GetMapping("/getRoleId")
    public ResultVo getRoleId(Long userId){
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserRole::getUserId,userId);
        UserRole one = userRoleService.getOne(queryWrapper);
        return ResultUtils.success("查询成功!",one);
    }

    @Auth
    @PostMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody UpdatePasswordParm parm, HttpServletRequest request){
        // 获取token
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        // 原密码
        String old = DigestUtils.md5DigestAsHex(parm.getOldPassword().getBytes());
        if (userType.equals("0")){ // 读者
            Reader reader = readerService.getById(parm.getUserId());
            // 密码对比
            if (!old.equals(reader.getPassword())){
                return ResultUtils.error("原密码错误");
            }
            Reader reader1 = new Reader();
            reader1.setPassword(DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()));
            reader1.setReaderId(parm.getUserId());
            boolean b = readerService.updateById(reader1);
            if (b){
                return ResultUtils.success("修改密码成功");
            }
        }else if (userType.equals("1")){ // 管理员
            User user = userService.getById(parm.getUserId());
            if (!user.getPassword().equals(old)){
                return ResultUtils.error("原密码错误");
            }
            User user1 = new User();
            user1.setPassword(DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()));
            user1.setUserId(parm.getUserId());
            boolean b = userService.updateById(user1);
            if (b){
                return ResultUtils.success("修改密码成功");
            }
        }
        return ResultUtils.error("密码修改失败");
    }

    // 重置密码
    @PostMapping("/resetPassword")
    public ResultVo resetPassword(@RequestBody User user){
        String password = "123456";
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        boolean b = userService.updateById(user);
        if (b){
            return ResultUtils.success("重置密码成功");
        }
        return ResultUtils.error("重置密码失败");
    }
}
