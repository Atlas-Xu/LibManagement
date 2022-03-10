package cn.xchub.web.menu.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.menu.entity.Menu;
import cn.xchub.web.menu.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    // 新增
    @Auth
    @PostMapping
    public ResultVo addMenu(@RequestBody Menu menu){
        menu.setCreateTime(new Date());
        boolean save = menuService.save(menu);
        if(save){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    // 编辑
    @Auth
    @PutMapping
    public ResultVo editMenu(@RequestBody Menu menu){
        menu.setUpdateTime(new Date());
        boolean save = menuService.updateById(menu);
        if(save){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    // 删除
    @Auth
    @DeleteMapping("/{menuId}")
    public ResultVo deleteMenu(@PathVariable("menuId") Long menuId){
        //判断是否有下级，有下级，不能删除
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().eq(Menu::getParentId,menuId);
        List<Menu> list = menuService.list(query);
        if(list.size() > 0){
            return ResultUtils.error("该菜单存在下级，不能删除!");
        }
        boolean save = menuService.removeById(menuId);
        if(save){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    // 菜单列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(){
        List<Menu> list = menuService.menuList();
        return ResultUtils.success("查询成功",list);
    }

    //上级菜单列表
    @Auth
    @GetMapping("/parent")
    public ResultVo getParentList(){
        List<Menu> list = menuService.parentList();
        return ResultUtils.success("查询成功",list);
    }
}
