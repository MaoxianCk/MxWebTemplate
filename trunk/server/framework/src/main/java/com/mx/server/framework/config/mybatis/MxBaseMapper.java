package com.mx.server.framework.config.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;


public interface MxBaseMapper<T> extends BaseMapper<T> {
    Integer physicalDeleteById(Serializable id);

    Integer physicalDeleteByBatchIds(@Param(Constants.COLL) Collection<?> idList);
}