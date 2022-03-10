package cn.xchub.web.menu.service;

import cn.xchub.web.menu.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuService extends IService<Menu> {
    // 菜单列表
    List<Menu> menuList();
    // 上级菜单列表
    List<Menu> parentList();
    // 根据用户id查询权限
    List<Menu> getMenuByUserId(Long userId);
    // 根据角色id查询权限
    List<Menu> getMenuByRoleId(Long roleId);
    // 根据读者id查询权限
    List<Menu> getMenuByReaderId(Long readerId);
}
