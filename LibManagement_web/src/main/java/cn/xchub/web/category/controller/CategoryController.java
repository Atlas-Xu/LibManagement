package cn.xchub.web.category.controller;

import cn.xchub.annotation.Auth;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import cn.xchub.web.category.entity.Category;
import cn.xchub.web.category.entity.CategoryEcharts;
import cn.xchub.web.category.entity.CategoryParam;
import cn.xchub.web.category.service.CategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //新增
    @Auth
    @PostMapping
    public ResultVo add(@RequestBody Category category){
        boolean save = categoryService.save(category);
        if (save){
            return ResultUtils.success("新增成功！");
        }
        return ResultUtils.error("新增失败！");
    }

    //编辑
    @Auth
    @PutMapping
    public ResultVo edit(@RequestBody Category category){
        boolean save = categoryService.updateById(category);
        if (save){
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败！");
    }

    // 删除
    @Auth
    @DeleteMapping("/{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId){
        boolean remove = categoryService.removeById(categoryId);
        if (remove){
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }

    // 列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(CategoryParam param){
        IPage<Category> list = categoryService.getList(param);
        return ResultUtils.success("查询成功！",list);
    }

    //图书列表分类
    @Auth
    @GetMapping("/cateList")
    public ResultVo getCateList(){
        List<Category> list = categoryService.list();
        return ResultUtils.success("查询成功", list);
    }

    //图书分类数量
    @Auth
    @GetMapping("/categoryCount")
    public ResultVo categoryCount(){
        CategoryEcharts categoryVo = categoryService.getCategoryVo();
        return ResultUtils.success("查询成功", categoryVo);
    }
}
