package cn.xchub.web.role.service;

import cn.xchub.web.role.entity.AssignParam;
import cn.xchub.web.role.entity.AssignVo;
import cn.xchub.web.role.entity.Role;
import cn.xchub.web.role.entity.RoleParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoleService extends IService<Role> {
    IPage<Role> list(RoleParam param);
    // 角色权限回显
    AssignVo getAssignShow(AssignParam param);
}
