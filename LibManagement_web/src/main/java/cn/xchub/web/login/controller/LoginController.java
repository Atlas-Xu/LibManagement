package cn.xchub.web.login.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.jwt.JwtUtils;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.login.entity.LoginParm;
import cn.xchub.web.login.entity.LoginResult;
import cn.xchub.web.login.entity.UserInfo;
import cn.xchub.web.menu.entity.MakeTree;
import cn.xchub.web.menu.entity.Menu;
import cn.xchub.web.menu.entity.RouterVo;
import cn.xchub.web.menu.service.MenuService;
import cn.xchub.web.reader.entity.Reader;
import cn.xchub.web.reader.service.ReaderService;
import cn.xchub.web.user.entity.User;
import cn.xchub.web.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/system")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MenuService menuService;

    // 用户登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm loginParm) {
        if (StringUtils.isEmpty(loginParm.getUsername()) || StringUtils.isEmpty(loginParm.getPassword()) || StringUtils.isEmpty(loginParm.getUserType())) {
            return ResultUtils.error("用户名、密码、用户类型不能为空!");
        }
        // 判断是读者还是管理员
        if (loginParm.getUserType().equals("0")) { //0 读者
            // 根据读者的用户名密码查询
            QueryWrapper<Reader> query = new QueryWrapper<>();
            query.lambda().eq(Reader::getUsername, loginParm.getUsername()).eq(Reader::getPassword, DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
            Reader one = readerService.getOne(query);
            if (one == null) {
                return ResultUtils.error("用户名或密码错误");
            }
            // 返回数据给前端
            LoginResult loginResult = new LoginResult();
            loginResult.setToken(jwtUtils.generateToken(one.getUsername(), loginParm.getUserType()));
            loginResult.setUserId(one.getReaderId());
            return ResultUtils.success("登录成功", loginResult);
        } else if (loginParm.getUserType().equals("1")) { // 1 管理员
            // 根据用户的用户名密码查询
            QueryWrapper<User> query = new QueryWrapper<>();
            query.lambda().eq(User::getUsername, loginParm.getUsername()).eq(User::getPassword, DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
            User one = userService.getOne(query);
            if (one == null) {
                return ResultUtils.error("用户名或密码错误");
            }
            // 返回数据给前端
            LoginResult loginResult = new LoginResult();
            loginResult.setToken(jwtUtils.generateToken(one.getUsername(), loginParm.getUserType()));
            loginResult.setUserId(one.getUserId());
            return ResultUtils.success("登录成功", loginResult);
        } else {
            return ResultUtils.error("用户类型不存在！");
        }
    }

    //获取用户权限字段
    @Auth
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long userId, HttpServletRequest request){
        // 从请求的头部获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            return ResultUtils.error("token过期！",600);
        }
        // 从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        // 定义用户信息类
        UserInfo userInfo = new UserInfo();
        if (userType.equals("0")){ // 读者
            // 根据id查询读者信息
            Reader reader = readerService.getById(userId);
            userInfo.setIntroduction(reader.getLearnNum());
            userInfo.setName(reader.getLearnNum());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            // 权限字段的查询与设置
            List<Menu> menuList = menuService.getMenuByReaderId(userId);
            List<String> collect = menuList.stream().filter(item -> item != null && item.getCode() != null).map(item -> item.getCode()).collect(Collectors.toList());
            if (collect.size() == 0){
                return ResultUtils.error("暂无登录权限,请联系管理员");
            }
            // 转为数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("查询成功！",userInfo);
        }else if (userType.equals("1")){ // 管理员
            User user = userService.getById(userId);
            userInfo.setIntroduction(user.getNickName());
            userInfo.setName(user.getNickName());
            // 头像写死，没空做了
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            // 权限字段的查询与设置
            List<Menu> menuList = menuService.getMenuByUserId(userId);
            List<String> collect = menuList.stream().filter(item -> item != null && item.getCode() != null).map(item -> item.getCode()).collect(Collectors.toList());
            if (collect.size() == 0){
                return ResultUtils.error("暂无登录权限,请联系管理员");
            }
            // 转为数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("查询成功！",userInfo);
        }else {
            return ResultUtils.error("用户类型不存在", userInfo);
        }
    }

    // 查询菜单
    @Auth
    @GetMapping("/getMenuList")
    public ResultVo getMenuList(HttpServletRequest request){
        // 获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            return ResultUtils.error("token过期！",600);
        }
        // 获取用户名或类型
        String username = jwtUtils.getUsernameFromToken(token);
        // 获取用户类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        if (userType.equals("0")){ // 读者
            // 获取读者信息
            Reader reader = readerService.loadByUsername(username);
            // 查询菜单信息
            List<Menu> menuList = menuService.getMenuByReaderId(reader.getReaderId());
            List<Menu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            if (collect.size() == 0){
                return ResultUtils.error("暂无登录权限，请联系管理员！");
            }
            // 组装路由格式的数据
            List<RouterVo> routerVos = MakeTree.makeRouter(collect, 0L);
            return ResultUtils.success("查询成功",routerVos);
        }else if (userType.equals("1")){ // 管理员
            // 获取用户信息
            User user = userService.loadByUsername(username);
            // 查询菜单信息
            List<Menu> menuList = menuService.getMenuByUserId(user.getUserId());
            List<Menu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            if (collect.size() == 0){
                return ResultUtils.error("暂无登录权限，请联系管理员！");
            }
            // 组装路由格式的数据
            List<RouterVo> routerVos = MakeTree.makeRouter(collect, 0L);
            return ResultUtils.success("查询成功",routerVos);
        }else {
            return ResultUtils.error("用户类型不存在");
        }
    }
}
