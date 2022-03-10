package cn.xchub.web.category.service.impl;

import cn.xchub.web.category.entity.Category;
import cn.xchub.web.category.entity.CategoryEcharts;
import cn.xchub.web.category.entity.CategoryParm;
import cn.xchub.web.category.entity.CategoryVo;
import cn.xchub.web.category.mapper.CategoryMapper;
import cn.xchub.web.category.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Override
    public IPage<Category> getList(CategoryParm parm) {
        // 构造分页对象
        IPage<Category> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());

        // 查询条件
        QueryWrapper<Category> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getCategoryName())){
            query.lambda().like(Category::getCategoryName,parm.getCategoryName());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public CategoryEcharts getCategoryVo() {
        CategoryEcharts echarts = new CategoryEcharts();
        List<CategoryVo> categoryVo = this.baseMapper.getCategoryVo();
        List<String> names = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        if(categoryVo.size() > 0){
            for(int i=0;i<categoryVo.size();i++){
                names.add(categoryVo.get(i).getCategoryName());
                counts.add(categoryVo.get(i).getBookCount());
            }
            echarts.setCounts(counts);
            echarts.setNames(names);
        }
        return echarts;
    }
}
