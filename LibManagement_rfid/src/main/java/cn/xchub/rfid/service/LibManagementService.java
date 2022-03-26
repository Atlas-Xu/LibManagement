package cn.xchub.rfid.service;

import cn.xchub.rfid.config.LibManagementProperties;
import cn.xchub.rfid.domain.GetBookRequest;
import cn.xchub.rfid.domain.GetBookResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
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
     * TODO RFID 转 BookCode
     */
    public String rfidToCode(String rfid) {
        final int index = Math.toIntExact(Instant.now().toEpochMilli() % hackBookCode.size());
        return hackBookCode.get(index);
    }

}
