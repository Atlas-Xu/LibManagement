package cn.xchub.rfid.controller;

import cn.xchub.rfid.domain.GetBookResponse;
import cn.xchub.rfid.service.LibManagementService;
import cn.xchub.utils.ResultUtils;
import cn.xchub.utils.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResultVo<Object> getBook() {
        final List<String> rfidResult = libManagementService.queryByRfid();
        final List<String> bookCodes = libManagementService.rfidToResultToBookCode(rfidResult);
        final List<GetBookResponse> books = bookCodes.stream().map(libManagementService::getBook)
                                                     .filter(Objects::nonNull)
                                                     .collect(toList());

        return ResultUtils.success("查询成功", books);
    }
}
