package com.mx.server.framework.controller;

import com.mx.server.framework.error.BusinessException;
import com.mx.server.framework.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maoxian
 * @since 2022/5/22
 * 通用功能，如验证码等
 */
@Tag(name = "CommonController", description = "通用功能模块")
@RestController
@RequestMapping("common")
public class CommonController {
    private final CommonService commonService;

    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    @Operation(summary = "获取验证码")
    @GetMapping("captcha")
    @ResponseBody
    public void captcha() throws BusinessException {
        commonService.getImgCaptcha();
    }

}
