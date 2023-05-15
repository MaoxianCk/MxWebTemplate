package com.mx.server.framework.model.vo.res;

import com.mx.server.framework.enums.EntityStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * @author Maoxian
 * @since 2023/5/15
 */
@Data
public class ResTreeNodeVO {
    private Long id;
    private String label;
    private String code;
    private String value;
    private List<ResTreeNodeVO> children;
}
