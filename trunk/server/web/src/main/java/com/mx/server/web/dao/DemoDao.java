package com.mx.server.web.dao;

import com.mx.server.web.model.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author Maoxian
 * @since 2023/4/9
 */
@Mapper
public interface DemoDao {
    DemoEntity selectOne();
}
