package com.mx.server.framework.model.vo.req;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReqDictSearchVO extends ReqPageVO {
    // 根据label模糊查询
    private String label;
    // 根据code模糊查询
    private String code;
    // 根据父级code查询
    private String parentCode;
    // 根据父级id查询
    private Integer parentId;
    // 状态
    private Integer status;
}
