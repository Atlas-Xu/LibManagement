package cn.xchub.web.menu.mapper;

import cn.xchub.web.menu.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    //根据用户id查询权限
    List<Menu> getMenuByUserId(@Param("userId") Long userId);
    //根据角色id查询权限
    List<Menu> getMenuByRoleId(@Param("roleId") Long roleId);
    //根据读者id查询权限
    List<Menu> getMenuByReaderId(@Param("readerId") Long readerId);
}
