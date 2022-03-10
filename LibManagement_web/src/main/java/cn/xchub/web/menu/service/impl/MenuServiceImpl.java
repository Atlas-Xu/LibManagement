package cn.xchub.web.menu.service.impl;

import cn.xchub.web.menu.entity.MakeTree;
import cn.xchub.web.menu.entity.Menu;
import cn.xchub.web.menu.mapper.MenuMapper;
import cn.xchub.web.menu.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> menuList() {
        // 查询列表
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(Menu::getOrderNum);
        List<Menu> menuList = this.baseMapper.selectList(query);
        //组装树
        List<Menu> list = MakeTree.makeMenuTree(menuList, 0L);
        return list;
    }

    @Override
    public List<Menu> parentList() {
        //只需要查询目录和菜单,不用查按钮
        String[] types = {"0","1"};
        //构造查询条件
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().in(Menu::getType, Arrays.asList(types)).orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(query);
        //构造顶级菜单,防止无上级菜单为null
        Menu menu = new Menu();
        menu.setMenuId(0L);
        menu.setParentId(-1L);
        menu.setTitle("顶级菜单");
        menus.add(menu);
        //构造树
        List<Menu> menus1 = MakeTree.makeMenuTree(menus, -1L);

        return menus1;
    }

    @Override
    public List<Menu> getMenuByUserId(Long userId) {
        return this.baseMapper.getMenuByUserId(userId);
    }

    @Override
    public List<Menu> getMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }

    @Override
    public List<Menu> getMenuByReaderId(Long readerId) {
        return this.baseMapper.getMenuByReaderId(readerId);
    }
}
