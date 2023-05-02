package com.mx.server.framework.dao;

import com.mx.server.framework.model.vo.req.ReqSearchListVO;
import com.mx.server.framework.model.entity.ParamEntity;
import com.mx.server.framework.config.mybatis.MxBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统参数表 Mapper 接口
 * </p>
 *
 * @author Maoxian
 * @since 2023-05-01
 */
@Mapper
public interface ParamMapper extends MxBaseMapper<ParamEntity> {

    List<ParamEntity> selectParamList(ReqSearchListVO req);

    ParamEntity selectByIdOrCode(@Param("id") Long id, @Param("code") String code);
}
