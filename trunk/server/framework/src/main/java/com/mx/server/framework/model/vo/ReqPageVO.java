package com.mx.server.framework.model.vo;

import lombok.Data;

@Data
public class ReqPageVO {
    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 每页条数
     */
    private Integer size = 10;
}
