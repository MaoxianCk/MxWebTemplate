package com.mx.server.framework.controller;

import com.mx.server.framework.model.response.CommonReturn;
import com.mx.server.framework.model.vo.req.ReqLoginVO;
import com.mx.server.framework.model.vo.req.ReqRegisterVO;
import com.mx.server.framework.model.vo.res.ResLoginVO;
import com.mx.server.framework.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class SysUserController {
    private final UserService userService;

    @PostMapping("login")
    @ResponseBody
    public CommonReturn<?> login(@RequestBody @Valid ReqLoginVO loginVO) {
        ResLoginVO res = userService.doLogin(loginVO);
        return CommonReturn.success(res);
    }

    @PostMapping("register")
    @ResponseBody
    public CommonReturn<?> register(@RequestBody @Valid ReqRegisterVO registerVO) {
        userService.doRegister(registerVO);
        return CommonReturn.success();
    }
}
