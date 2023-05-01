package com.mx.server.framework.config.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;


public interface MxBaseMapper<T> extends BaseMapper<T> {
    Integer physicalDeleteById(Serializable id);
}