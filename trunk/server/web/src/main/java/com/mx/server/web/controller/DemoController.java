package com.mx.server.web.controller;

import com.mx.server.framework.model.response.CommonReturn;
import com.mx.server.web.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maoxian
 * @since 2023/4/9
 */
@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;

    @GetMapping("getFromDb")
    @ResponseBody
    private CommonReturn<?> getFromDb() {
        var data = demoService.getFromDb();
        return CommonReturn.success(data);
    }
}
