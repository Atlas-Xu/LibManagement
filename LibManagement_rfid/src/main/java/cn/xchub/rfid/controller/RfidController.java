package cn.xchub.rfid.controller;

import cn.xchub.rfid.domain.RfidRequest;
import cn.xchub.rfid.service.LibManagementService;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/uhfapi/rfid")
public class RfidController {
    private final LibManagementService libManagementService;

    public RfidController(LibManagementService libManagementService) {
        this.libManagementService = libManagementService;
    }

    @PostMapping
    public ResultVo<Object> rfidQuery(@RequestBody RfidRequest request) {
        final List<String> rfids = request.getRfids()
                .stream()
                .filter(Objects::nonNull)
                .filter(it -> !it.isEmpty())
                .collect(toList());

        final List<String> bookCodes = rfids.stream()
                .map(libManagementService::rfidToCode)
                .filter(Objects::nonNull)
                .filter(it -> !it.isEmpty())
                .collect(toList());

        return ResultUtils.success(
                "查询成功",
                bookCodes.stream()
                        .map(libManagementService::getBook)
                        .filter(Objects::nonNull)
                        .collect(toList())
        );

    }
}
