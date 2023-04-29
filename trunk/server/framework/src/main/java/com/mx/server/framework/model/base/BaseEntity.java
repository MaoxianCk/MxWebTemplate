package com.mx.server.framework.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mx.server.framework.enums.EntityDeletedEnum;
import com.mx.server.framework.enums.EntityStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Maoxian
 * @since 2023/4/29
 */
@Data
public class BaseEntity {
    /**
     * 状态
     */
    private EntityStatusEnum status;

    /**
     * 是否删除
     */
    private EntityDeletedEnum deleted;

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
