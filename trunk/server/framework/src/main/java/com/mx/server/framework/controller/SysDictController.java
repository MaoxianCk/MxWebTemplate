package com.mx.server.framework.controller;

import com.mx.server.framework.model.entity.DictEntity;
import com.mx.server.framework.model.response.CommonReturn;
import com.mx.server.framework.model.vo.req.ReqDeleteVO;
import com.mx.server.framework.model.vo.req.ReqDictSearchVO;
import com.mx.server.framework.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public CommonReturn<?> getDictList(ReqDictSearchVO req) {
        return CommonReturn.success(dictService.getDictList(req));
    }
    @ResponseBody
    @GetMapping("getDictTree")
    public CommonReturn<?> getDictTree() {
        return CommonReturn.success(dictService.getDictTree());
    }
    @PostMapping("upsertDict")
    @ResponseBody
    public CommonReturn<?> upsertDict(@RequestBody DictEntity dictEntity) {
        dictService.upsertDict(dictEntity);
        return CommonReturn.success();
    }

    @PostMapping("deleteDict")
    @ResponseBody
    public CommonReturn<?> deleteDict(@RequestBody ReqDeleteVO reqDeleteVO) {
        dictService.deleteDict(reqDeleteVO);
        return CommonReturn.success();
    }
}
