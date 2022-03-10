package cn.xchub.web.role.service;

import cn.xchub.web.role.entity.AssignParm;
import cn.xchub.web.role.entity.AssignVo;
import cn.xchub.web.role.entity.Role;
import cn.xchub.web.role.entity.RoleParm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoleService extends IService<Role> {
    IPage<Role> list(RoleParm parm);
    // 角色权限回显
    AssignVo getAssignShow(AssignParm parm);
}
