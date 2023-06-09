package com.mx.server.framework.model.vo.req;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReqSearchVO {
    private String keyword;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
