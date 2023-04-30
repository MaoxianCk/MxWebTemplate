package com.mx.server.framework.controller;

import com.mx.server.framework.model.response.CommonReturn;
import com.mx.server.framework.model.vo.ReqSearchListVO;
import com.mx.server.framework.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maoxian
 * @since 2023/4/29
 */

@RestController
@RequestMapping("dict")
@RequiredArgsConstructor
public class SysDictController {
    public final DictService dictService;
    @ResponseBody
    @GetMapping("getDictList")
    public CommonReturn<?> getDictList(ReqSearchListVO req) {
        return CommonReturn.success(dictService.getDictList(req));
    }
}
