package com.mx.server.framework.dao;

import com.mx.server.framework.model.entity.DictEntity;
import com.mx.server.framework.config.mybatis.MxBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author Maoxian
 * @since 2023-05-01
 */
@Mapper
public interface DictMapper extends MxBaseMapper<DictEntity> {

}
