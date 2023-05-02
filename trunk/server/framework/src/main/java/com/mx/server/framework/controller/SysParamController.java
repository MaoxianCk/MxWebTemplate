package com.mx.server.framework.controller;

import com.mx.server.framework.model.vo.req.ReqDeleteVO;
import com.mx.server.framework.model.vo.req.ReqSearchListVO;
import com.mx.server.framework.model.entity.ParamEntity;
import com.mx.server.framework.model.response.CommonReturn;
import com.mx.server.framework.service.ParamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sysParam")
@RequiredArgsConstructor
public class SysParamController {
    private final ParamService paramService;


    @GetMapping("getParamList")
    @ResponseBody
    public CommonReturn<?> getParamList(ReqSearchListVO req) {
        return CommonReturn.success(paramService.getParamList(req));
    }

    @GetMapping("getParam")
    @ResponseBody
    public CommonReturn<?> getParam(@RequestParam(value = "id", required = false) Long id,
                                    @RequestParam(value = "code", required = false) String code) {
        return CommonReturn.success(paramService.getParam(id, code));
    }

    @PostMapping("upsertParam")
    @ResponseBody
    public CommonReturn<?> upsertParam(@RequestBody ParamEntity paramEntity) {
        paramService.upsertParam(paramEntity);
        return CommonReturn.success();
    }

    @PostMapping("deleteParam")
    @ResponseBody
    public CommonReturn<?> deleteParam(@RequestBody ReqDeleteVO reqDeleteVO) {
        paramService.deleteParam(reqDeleteVO);
        return CommonReturn.success();
    }
}
