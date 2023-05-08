package com.mx.server.framework.dao;

import com.mx.server.framework.model.entity.DictEntity;
import com.mx.server.framework.config.mybatis.MxBaseMapper;
import com.mx.server.framework.model.vo.req.ReqDictSearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author Maoxian
 * @since 2023-04-30
 */
@Mapper
public interface DictMapper extends MxBaseMapper<DictEntity> {
    List<DictEntity> selectDictList(ReqDictSearchVO req);
}
