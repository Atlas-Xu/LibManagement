package com.zsc.smartcafeteriaequipment.utils;

import UHF.UHFReader;
import com.zsc.smartcafeteriaequipment.service.EquipClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class RFIDReader {
    private static final Logger log = LoggerFactory.getLogger(RFIDReader.class);
    String equipName = "equip1";

    private final EquipClient equipClient;

    UHFReader uhf = new UHFReader();

    public RFIDReader(EquipClient equipClient) {
        this.equipClient = equipClient;
    }

    @PostConstruct
    @Async
    public void OpenCom() {
        byte ComAddr = (byte) 255;
        byte baudRate = (byte) 5;
        int fCmdRet = uhf.AutoOpenCom(ComAddr, baudRate);
        if (fCmdRet == 0) {
            log.info("打开串口成功");
            query();
        } else {
            log.info("打开串口失败");
        }
    }

    @Async
    @Scheduled(cron = "0/1 * * * * *")
    public List<String> query() {
        String[] EPC = uhf.Inventory();
        if (EPC == null) return null;

        List<String> list = new ArrayList<>(Arrays.asList(EPC));
        Set<String> set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
        equipClient.sendTag(list, equipName);
        return list;
    }
}
