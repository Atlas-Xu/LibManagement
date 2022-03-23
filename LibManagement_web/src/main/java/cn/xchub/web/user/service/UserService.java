package cn.xchub.web.user.service;

import cn.xchub.web.user.entity.PageParam;
import cn.xchub.web.user.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    IPage<User> list(PageParam param);
    void addUser(User user);
    void editUser(User user);
    User loadByUsername(String username);
}
