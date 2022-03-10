package cn.xchub.web.menu.entity;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MakeTree {
    public static List<Menu> makeMenuTree(List<Menu> menuList, Long pid){
        List<Menu> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream().filter(item -> item != null && item.getParentId() == pid)
                .forEach(dom ->{
                    Menu menu = new Menu();
                    BeanUtils.copyProperties(dom,menu);
                    // 查询下级菜单
                    List<Menu> menus = makeMenuTree(menuList,dom.getMenuId());
                    menu.setChildren(menus);
                    list.add(menu);
                });
        return list;
    }

    /**
     * 生成路由数据格式
     * 即vue-admin-template的路由配置格式，对应async部分
     */
    public static List<RouterVo> makeRouter(List<Menu> menuList, Long pid){
        //接受生产的路由数据
        List<RouterVo> list = new ArrayList<>();
        //组装数据
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item ->item != null && item.getParentId() == pid)
                .forEach(item ->{
                    RouterVo router = new RouterVo();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    //判断是否是一级菜单
                    if(item.getParentId() == 0L){
                        router.setComponent("Layout");
                        router.setAlwaysShow(true);
                    }else{
                        router.setComponent(item.getUrl());
                        router.setAlwaysShow(false);
                    }
                    //设置meta
                    router.setMeta(router.new Meta(
                            item.getTitle(),
                            item.getIcon(),
                            item.getCode().split(",")
                    ));
                    //设置children
                    List<RouterVo> children = makeRouter(menuList, item.getMenuId());
                    router.setChildren(children);
                    if(router.getChildren().size() > 0){
                        router.setAlwaysShow(true);
                    }
                    list.add(router);
                });
        return list;
    }
}
