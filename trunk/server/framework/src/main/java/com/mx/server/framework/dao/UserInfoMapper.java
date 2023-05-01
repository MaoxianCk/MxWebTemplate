package com.mx.server.framework.dao;

import com.mx.server.framework.model.entity.UserInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户信息表 Mapper 接口
 * </p>
 *
 * @author Maoxian
 * @since 2023-04-30
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {

}
