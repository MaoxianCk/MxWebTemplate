package com.mx.server.web.controller;

import com.mx.server.framework.dao.UserMapper;
import com.mx.server.framework.model.entity.UserEntity;
import com.mx.server.framework.model.response.CommonReturn;
import com.mx.server.web.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Maoxian
 * @since 2023/4/9
 */
@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;
    private final UserMapper userMapper;

    @GetMapping("getFromDb")
    @ResponseBody
    private CommonReturn<?> getFromDb() {
        var data = userMapper.selectOne(null);
        return CommonReturn.success(data);
    }

    @PostMapping("addOne")
    @ResponseBody
    private CommonReturn<?> addOne(@RequestBody UserEntity userEntity) {
        userMapper.insert(userEntity);
        userEntity.setPwd("123");
        userMapper.updateById(userEntity);
        return CommonReturn.success();
    }



    @PostMapping("updateOne")
    @ResponseBody
    private CommonReturn<?> updateOne(@RequestBody UserEntity userEntity) {
        userMapper.updateById(userEntity);
        return CommonReturn.success();
    }
}
