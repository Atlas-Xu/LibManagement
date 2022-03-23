package cn.xchub.web.role.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.role.entity.*;
import cn.xchub.web.role.service.RoleService;
import cn.xchub.web.role_menu.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    // 新增
    @Auth
    @PostMapping
    public ResultVo addRole(@RequestBody Role role) {
        if (role.getRoleType().equals("2")) {
            QueryWrapper<Role> query = new QueryWrapper<>();
            query.lambda().eq(Role::getRoleType, "2");
            Role one = roleService.getOne(query);
            if (one != null) {
                return ResultUtils.error("读者角色已经存在!");
            }
        }
        role.setCreateTime(new Date());
        boolean save = roleService.save(role);
        if (save) {
            return ResultUtils.success("新增角色成功!");
        }
        return ResultUtils.error("新增角色失败!");
    }

    // 编辑
    @Auth
    @PutMapping
    public ResultVo editRole(@RequestBody Role role) {
        if (role.getRoleType().equals("2")) {
            QueryWrapper<Role> query = new QueryWrapper<>();
            query.lambda().eq(Role::getRoleType, "2");
            Role one = roleService.getOne(query);
            if (one != null && role.getRoleId() != one.getRoleId()) {
                return ResultUtils.error("读者角色已经存在!");
            }
        }
        role.setCreateTime(new Date());
        boolean save = roleService.updateById(role);
        if (save) {
            return ResultUtils.success("编辑角色成功!");
        }
        return ResultUtils.error("编辑角色失败!");
    }

    // 删除
    @Auth
    @DeleteMapping("/{roleId}")
    public ResultVo deleteRole(@PathVariable("roleId") Long roleId) {
        boolean remove = roleService.removeById(roleId);
        if (remove) {
            return ResultUtils.success("删除角色成功!");
        }
        return ResultUtils.error("删除角色失败!");
    }

    // 列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(RoleParam param) {
        IPage<Role> list = roleService.list(param);
        return ResultUtils.success("查询成功!", list);
    }

    // 查询角色权限树的回显
    @Auth
    @GetMapping("/getAssignShow")
    public ResultVo getAssignShow(AssignParam param){
        AssignVo show = roleService.getAssignShow(param);
        return ResultUtils.success("查询成功！", show);
    }

    // 角色分配权限保存
    @Auth
    @PostMapping("/assignSave")
    public ResultVo assignSave(@RequestBody SaveAssign param){
        roleMenuService.assignSave(param.getRoleId(),param.getList());
        return ResultUtils.success("分配成功！");
    }

}
