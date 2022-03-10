package cn.xchub.web.user.service.impl;

import cn.xchub.web.user.entity.PageParm;
import cn.xchub.web.user.entity.User;
import cn.xchub.web.user.mapper.UserMapper;
import cn.xchub.web.user.service.UserService;
import cn.xchub.web.user_role.entity.UserRole;
import cn.xchub.web.user_role.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public IPage<User> list(PageParm parm){
        //分页
        IPage<User> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //查询条件
        QueryWrapper<User> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNickName())){
            query.lambda().like(User::getNickName,parm.getNickName());
        }
        if(StringUtils.isNotEmpty(parm.getPhone())){
            query.lambda().like(User::getPhone,parm.getPhone());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        // 插入用户
        int insert = this.baseMapper.insert(user);
        if (insert > 0){
            // 插入用户对应的角色
            UserRole userRole = new UserRole();
            userRole.setRoleId(user.getRoleId());
            userRole.setUserId(user.getUserId());
            userRoleService.save(userRole);
        }
    }

    @Override
    public void editUser(User user) {
        int i = this.baseMapper.updateById(user);
        // 先删除原角色再重新插入
        if (i>0){
            QueryWrapper<UserRole> query = new QueryWrapper<>();
            query.lambda().eq(UserRole::getUserId,user.getUserId());
            userRoleService.remove(query);
            // 插入
            UserRole userRole = new UserRole();
            userRole.setRoleId(user.getRoleId());
            userRole.setUserId(user.getUserId());
            userRoleService.save(userRole);
        }
    }

    @Override
    public User loadByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername,username);
        return this.baseMapper.selectOne(queryWrapper);
    }
}
