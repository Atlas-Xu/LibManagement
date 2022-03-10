package cn.xchub.web.menu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由需要的数据格式
 * 根据vue-admin-template路由菜单配置项
 */

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo {
    private String path;

    private String component;

    private boolean alwaysShow;

    private String name;

    private Meta meta;

    @Data
    @AllArgsConstructor
    public class Meta {
        private String title;
        private String icon;
        private Object[] roles;
    }
    private List<RouterVo> children =new ArrayList<>();
}
