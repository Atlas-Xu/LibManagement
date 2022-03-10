package cn.xchub.web.reader_role.service.impl;

import cn.xchub.web.reader_role.entity.ReaderRole;
import cn.xchub.web.reader_role.mapper.ReaderRoleMapper;
import cn.xchub.web.reader_role.service.ReaderRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ReaderRoleServiceImpl extends ServiceImpl<ReaderRoleMapper, ReaderRole> implements ReaderRoleService {
}
