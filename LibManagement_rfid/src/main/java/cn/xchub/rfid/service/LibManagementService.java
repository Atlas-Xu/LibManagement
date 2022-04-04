package cn.xchub.rfid.service;

import UHF.UHFReader;
import cn.xchub.rfid.config.LibManagementProperties;
import cn.xchub.rfid.domain.GetBookRequest;
import cn.xchub.rfid.domain.GetBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class LibManagementService {
    private final RestTemplate rest;
    private final LibManagementProperties libManagementProperties;

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

    final UHFReader uhf = new UHFReader();

    /**
     * 开关读卡器端口用的
     */
    @PostConstruct
    @Async
    public void OpenCom() {
        byte ComAddr = (byte) 255;
        byte baudRate = (byte) 5;
        int fCmdRet = uhf.AutoOpenCom(ComAddr, baudRate);
        if (fCmdRet == 0) {
            log.info("打开串口成功");
            List<String> rfidResult = queryByRfid();
            log.info("Rfid result: {}", rfidResult);
        } else {
            log.info("打开串口失败");
        }
    }

    public List<String> queryByRfid() {
        String[] epc = uhf.Inventory();
        if (epc == null) return null;

        List<String> result = new ArrayList<>(Arrays.asList(epc));
        return result.stream().distinct().collect(toList());
    }

    public List<String> rfidToResultToBookCode(List<String> rfidResult) {
        return rfidResult.stream()
                         .filter(Objects::nonNull)
                         .filter(it -> !it.isEmpty())
                         .filter(it -> it.length() >= 13)
                         .map(it -> it.substring(13))
                         .collect(toList());
    }
}
