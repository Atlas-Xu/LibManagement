package cn.xchub.rfid.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("lib-management")
public class LibManagementProperties {
    /**
     * 图书后台系统地址
     */
    private String applicationUrl;

    /**
     * 获得图书信息地址
     */
    private String getBookByCodePath;
}
