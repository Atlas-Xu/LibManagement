package cn.xchub.web.role.service.impl;

import cn.xchub.web.menu.entity.MakeTree;
import cn.xchub.web.menu.entity.Menu;
import cn.xchub.web.menu.service.MenuService;
import cn.xchub.web.role.entity.AssignParam;
import cn.xchub.web.role.entity.AssignVo;
import cn.xchub.web.role.entity.Role;
import cn.xchub.web.role.entity.RoleParam;
import cn.xchub.web.role.mapper.RoleMapper;
import cn.xchub.web.role.service.RoleService;
import cn.xchub.web.user.entity.User;
import cn.xchub.web.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @Override
    public IPage<Role> list(RoleParam param) {
        //构造分页对象
        IPage<Role> page = new Page<>();
        page.setSize(param.getPageSize());
        page.setCurrent(param.getCurrentPage());
        //查询条件
        QueryWrapper<Role> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(param.getRoleName())){
            query.lambda().like(Role::getRoleName,param.getRoleName());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public AssignVo getAssignShow(AssignParam param) {
        // 查询当前用户信息
        User user = userService.getById(param.getUserId());
        // 菜单数据
        List<Menu> list = null;
        if (user.getIsAdmin().equals("1")){ // 超管
            QueryWrapper<Menu> query = new QueryWrapper<>();
            query.lambda().orderByAsc(Menu::getOrderNum);
            list = menuService.list(query);
        }else {
            list = menuService.getMenuByUserId(user.getUserId());
        }
        // 组装树
        List<Menu> menuList = MakeTree.makeMenuTree(list, 0L);
        // 查询角色原来的菜单
        List<Menu> roleList = menuService.getMenuByRoleId(param.getRoleId());
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(roleList).orElse(new ArrayList<>()).stream().filter(item -> item!=null).forEach(item ->{
            ids.add(item.getMenuId());
        });
        // 组织数据
        AssignVo vo = new AssignVo();
        vo.setMenuList(menuList);
        vo.setCheckList(ids.toArray());
        return vo;
    }
}
