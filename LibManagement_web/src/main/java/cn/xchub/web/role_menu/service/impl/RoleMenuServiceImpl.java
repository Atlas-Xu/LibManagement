package cn.xchub.web.role_menu.service.impl;

import cn.xchub.web.role_menu.entity.RoleMenu;
import cn.xchub.web.role_menu.mapper.RoleMenuMapper;
import cn.xchub.web.role_menu.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Override
    @Transactional
    public void assignSave(Long roleId, List<Long> menuList) {
        // 删除角色原来的菜
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId, roleId);
        this.baseMapper.delete(query);
        // 插入新的菜单
        this.baseMapper.assignSave(roleId,menuList);
    }
}
