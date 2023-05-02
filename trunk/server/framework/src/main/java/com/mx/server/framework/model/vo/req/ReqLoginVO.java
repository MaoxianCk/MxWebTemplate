package com.mx.server.framework.model.vo.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ReqLoginVO {
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String pwd;
}
