package cn.xchub.web.role_menu.mapper;

import cn.xchub.web.role_menu.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    // 保存角色权限
    void assignSave(@Param("roleId") Long roleId, @Param("menuList")List<Long> menuList);
}
