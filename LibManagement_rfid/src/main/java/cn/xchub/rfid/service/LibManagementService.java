package cn.xchub.rfid.service;

import UHF.UHFReader;
import cn.xchub.rfid.config.LibManagementProperties;
import cn.xchub.rfid.domain.GetBookRequest;
import cn.xchub.rfid.domain.GetBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private static final UHFReader UHF = new UHFReader();

    static {
        openCom();
    }

    public static void openCom() {
        byte comAddr = (byte) 255;
        byte baudRate = (byte) 5;
        int fCmdRet = UHF.AutoOpenCom(comAddr, baudRate);
        log.info("打开串口 {}", fCmdRet == 0 ? "成功" : "失败");
    }

    public List<String> queryByRfid() {
        String[] epc = UHF.Inventory();
        if (epc == null) return null;

        List<String> result = new ArrayList<>(Arrays.asList(epc));
        log.info("Rfid result: {}", result);
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
