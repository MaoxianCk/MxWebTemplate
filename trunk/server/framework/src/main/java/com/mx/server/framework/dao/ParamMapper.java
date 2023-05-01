package com.mx.server.framework.dao;

import com.mx.server.framework.model.vo.ReqSearchListVO;
import com.mx.server.framework.model.entity.ParamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 系统参数表 Mapper 接口
 * </p>
 *
 * @author Maoxian
 * @since 2023-04-30
 */
@Mapper
public interface ParamMapper extends BaseMapper<ParamEntity> {

    List<ParamEntity> selectParamList(ReqSearchListVO req);
}
