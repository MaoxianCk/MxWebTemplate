package com.mx.server.framework.dao;

import com.mx.server.framework.model.entity.DictEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author Maoxian
 * @since 2023-04-29
 */
@Mapper
public interface DictMapper extends BaseMapper<DictEntity> {

}
