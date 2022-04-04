package cn.xchub.rfid.service;

import UHF.UHFReader;
import cn.xchub.rfid.config.LibManagementProperties;
import cn.xchub.rfid.domain.GetBookRequest;
import cn.xchub.rfid.domain.GetBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class LibManagementService {
    private final RestTemplate rest;
    private final LibManagementProperties libManagementProperties;

    private int fCmdRet = 0x30;
    UHFReader uhf = new UHFReader();

    public LibManagementService(LibManagementProperties libManagementProperties) {
        this.rest = new RestTemplateBuilder()
                .rootUri(libManagementProperties.getApplicationUrl())
                .build();
        this.libManagementProperties = libManagementProperties;
    }


    private GetBookResponse fetch(GetBookRequest request) {
        return rest.postForObject(
                libManagementProperties.getGetBookByCodePath(),
                request,
                GetBookResponse.class
        );
    }


    public GetBookResponse getBook(String bookCode) {
        return fetch(new GetBookRequest(bookCode));
    }


    private static final List<String> hackBookCode = Arrays.asList(
            "974651231",
            "4651313213",
            "646313132",
            "7846113213",
            "71313456466",
            "7841354646",
            "789461312321",
            "9746512345"
    );

    /**
     *
     * 开关读卡器端口用的
     * */

    @PostConstruct
    @Async
    public void OpenCom() {
        byte ComAddr = (byte) 255;
        byte baudRate = (byte) 5;
        fCmdRet = uhf.AutoOpenCom(ComAddr, baudRate);
        if (fCmdRet == 0) {
            log.info("打开串口成功");
            query();
            log.info(query().get(0));
        } else {
            log.info("打开串口失败");
        }
    }

    /**
     * 读RFID数据块的函数。读出的是EPC
     * 多张放一起就报错error create beans，是不是加了硬件的参数，所以注入有问题(此处随便写在service里面测试的，如果需要改就把这个和上面那个硬件的函数改在别的地方就可)
     *
     */
    // TODO 该函数每次请求接口时调用
    @Async
    @Scheduled(cron = "0/1 * * * * *")
    public List<String> query(){
        Set<String> set = new LinkedHashSet<>();
        List<String> list = new ArrayList<>();
        String EPC[] = uhf.Inventory();
        if (EPC == null) return null;

        for (String val : EPC) {
            list.add(val);
        }
        set.addAll(list);
        list.clear();
        list.addAll(set);
        // 此处读取出的即为所有书本的ISBN号，每个EPC为16位，ISBN为13为，多3位
        return list;
    }

    /**
     * TODO RFID 转 BookCode
     * EPC数据去掉最后3位即为ISBN
     */
    public String rfidToCode(String rfid) {
        final int index = Math.toIntExact(Instant.now().toEpochMilli() % hackBookCode.size());
        return hackBookCode.get(index);
    }

}
