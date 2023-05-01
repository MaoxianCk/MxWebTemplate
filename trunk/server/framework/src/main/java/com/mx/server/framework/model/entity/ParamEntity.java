package com.mx.server.framework.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mx.server.framework.model.base.BaseEntity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统参数表
 * </p>
 *
 * @author Maoxian
 * @since 2023-05-01
 */
@Getter
@Setter
@TableName("s_param")
public class ParamEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 值
     */
    @TableField("value")
    private String value;

    /**
     * 说明
     */
    @TableField("description")
    private String description;
}
