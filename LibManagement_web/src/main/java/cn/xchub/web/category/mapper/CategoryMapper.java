package cn.xchub.web.category.mapper;

import cn.xchub.web.category.entity.Category;
import cn.xchub.web.category.entity.CategoryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {
    List<CategoryVo> getCategoryVo();
}
