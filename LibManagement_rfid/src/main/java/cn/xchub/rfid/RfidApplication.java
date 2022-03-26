package cn.xchub.rfid;

import cn.xchub.rfid.config.LibManagementProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(
        LibManagementProperties.class
)
public class RfidApplication {
    public static void main(String[] args) {
        SpringApplication.run(RfidApplication.class, args);
    }
}
