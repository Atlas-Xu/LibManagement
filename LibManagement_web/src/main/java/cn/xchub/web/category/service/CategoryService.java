package cn.xchub.web.category.service;

import cn.xchub.web.category.entity.Category;
import cn.xchub.web.category.entity.CategoryEcharts;
import cn.xchub.web.category.entity.CategoryParm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {
    IPage<Category> getList(CategoryParm parm);
    CategoryEcharts getCategoryVo();
}
