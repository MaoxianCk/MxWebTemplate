package com.mx.server.framework.model.vo.req;

import lombok.Data;

import java.util.List;

@Data
public class ReqDeleteVO {
    private List<String> ids;
}
