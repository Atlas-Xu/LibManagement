package cn.xchub.web.user_role.service.impl;

import cn.xchub.web.user_role.entity.UserRole;
import cn.xchub.web.user_role.mapper.UserRoleMapper;
import cn.xchub.web.user_role.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
