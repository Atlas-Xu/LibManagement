package com.zsc.smartcafeteriaequipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
public class SmartcafeteriaEquipmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartcafeteriaEquipmentApplication.class, args);
    }

}
