package cn.xchub.web.role_menu.service;

import cn.xchub.web.role_menu.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenu> {
    // 保存角色权限
    void assignSave(Long roleId, List<Long> menuList);
}
