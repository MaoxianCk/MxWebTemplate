package com.mx.server.framework.model.vo.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReqSearchListVO extends ReqPageVO {
    private String keyword;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
