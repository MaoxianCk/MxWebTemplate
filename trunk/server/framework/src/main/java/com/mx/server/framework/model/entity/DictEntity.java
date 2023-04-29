package com.mx.server.framework.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author Maoxian
 * @since 2023-04-29
 */
@Getter
@Setter
@TableName("s_dict")
public class DictEntity implements Serializable {

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
     * 状态(0正常/1停用)
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("description")
    private String description;

    /**
     * 创建者
     */
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
