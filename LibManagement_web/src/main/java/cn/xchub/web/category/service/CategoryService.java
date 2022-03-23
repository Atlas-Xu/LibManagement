package cn.xchub.web.category.service;

import cn.xchub.web.category.entity.Category;
import cn.xchub.web.category.entity.CategoryEcharts;
import cn.xchub.web.category.entity.CategoryParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CategoryService extends IService<Category> {
    IPage<Category> getList(CategoryParam param);
    CategoryEcharts getCategoryVo();
}
