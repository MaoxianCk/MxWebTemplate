package com.mx.server.framework.model.vo.req;

import java.time.LocalDateTime;

public class ReqDictSearchVO extends ReqPageVO {
    // 根据label模糊查询
    private String label="1";
    // 根据code模糊查询
    private String code="1";
    // 根据父级code查询
    private String parentCode="1";
    // 根据父级id查询
    private Integer parentId=1;
    // 状态
    private Integer status=0;
}
