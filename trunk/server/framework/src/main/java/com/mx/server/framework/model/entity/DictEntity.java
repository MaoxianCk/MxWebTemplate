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
 * 字典数据表
 * </p>
 *
 * @author Maoxian
 * @since 2023-04-30
 */
@Getter
@Setter
@TableName("s_dict")
public class DictEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父级id null 表示字典类型
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 父级编码 null表示字典类型
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * 字典编码
     */
    @TableField("code")
    private String code;

    /**
     * 字典标签
     */
    @TableField("label")
    private String label;

    /**
     * 字典键值
     */
    @TableField("value")
    private String value;

    /**
     * 字典排序
     */
    @TableField("orders")
    private Integer orders;

    /**
     * 是否默认(1是/0否)
     */
    @TableField("is_default")
    private String isDefault;

    /**
     * 备注
     */
    @TableField("description")
    private String description;
}
