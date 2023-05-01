package com.mx.server.framework.dao;

import com.mx.server.framework.model.entity.UserInfoEntity;
import com.mx.server.framework.config.mybatis.MxBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户信息表 Mapper 接口
 * </p>
 *
 * @author Maoxian
 * @since 2023-05-01
 */
@Mapper
public interface UserInfoMapper extends MxBaseMapper<UserInfoEntity> {

}
