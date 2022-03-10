package cn.xchub.web.reader.service.impl;

import cn.xchub.exception.BusinessException;
import cn.xchub.web.reader.entity.ReaderParm;
import cn.xchub.web.reader.entity.Reader;
import cn.xchub.web.reader.mapper.ReaderMapper;
import cn.xchub.web.reader.service.ReaderService;
import cn.xchub.web.reader_role.entity.ReaderRole;
import cn.xchub.web.reader_role.service.ReaderRoleService;
import cn.xchub.web.role.entity.Role;
import cn.xchub.web.role.service.RoleService;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReaderServiceImpl extends ServiceImpl<ReaderMapper, Reader> implements ReaderService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private ReaderRoleService readerRoleService;

    @Override
    public IPage<Reader> getList(ReaderParm parm) {
        // 构造查询条件(拼接like查询)
        QueryWrapper<Reader> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getIdCard())){
            query.lambda().like(Reader::getIdCard,parm.getIdCard());
        }
        if (StringUtils.isNotEmpty(parm.getLearnNum())){
            query.lambda().like(Reader::getLearnNum,parm.getLearnNum());
        }
        if (StringUtils.isNotEmpty(parm.getPhone())){
            query.lambda().like(Reader::getPhone,parm.getPhone());
        }
        if (StringUtils.isNotEmpty(parm.getUsername())){
            query.lambda().like(Reader::getUsername,parm.getUsername());
        }

        //构造分页对象
        IPage<Reader> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.selectPage(page, query);
    }

    @Override
    @Transactional
    public void saveReader(Reader reader) {
        QueryWrapper<Role> query = new QueryWrapper<>();
        query.lambda().eq(Role::getRoleType,"2");
        Role one = roleService.getOne(query);
        if (one == null){
            throw new BusinessException(500,"请先建立读者角色后再建立读者");
        }
        // 新增读者
        this.baseMapper.insert(reader);
        // 设置读者角色
        ReaderRole readerRole = new ReaderRole();
        readerRole.setReaderId(reader.getReaderId());
        readerRole.setRoleId(one.getRoleId());
        readerRoleService.save(readerRole);
    }

    @Override
    @Transactional
    public void editReader(Reader reader) {
        // 编辑读者
        this.baseMapper.updateById(reader);

        QueryWrapper<Role> query = new QueryWrapper<>();
        query.lambda().eq(Role::getRoleType,"2");
        Role one = roleService.getOne(query);

        // 设置读者
        QueryWrapper<ReaderRole> readerRoleQueryWrapper = new QueryWrapper<>();
        readerRoleQueryWrapper.lambda().eq(ReaderRole::getReaderId,reader.getReaderId());
        // 删除
        readerRoleService.remove(readerRoleQueryWrapper);
        // 设置读者角色
        ReaderRole readerRole = new ReaderRole();
        readerRole.setReaderRoleId(reader.getReaderId());
        readerRole.setRoleId(one.getRoleId());
        readerRoleService.save(readerRole);
    }

    @Override
    public Reader loadByUsername(String username) {
        QueryWrapper<Reader> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Reader::getUsername,username);
        return this.baseMapper.selectOne(queryWrapper);
    }
}
