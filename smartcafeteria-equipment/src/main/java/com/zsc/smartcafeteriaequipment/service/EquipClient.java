package com.zsc.smartcafeteriaequipment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SMARTFETETIA", url = "http://121.5.110.15:8762")
@Component
public interface EquipClient {
    @PostMapping("/api/test")
    void sendTag(List<String> Tags, @RequestParam("equipName") String equipName);
}
