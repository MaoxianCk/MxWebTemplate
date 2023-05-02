package com.mx.server.framework.service;

import com.mx.server.framework.model.vo.req.ReqDeleteVO;
import com.mx.server.framework.model.vo.req.ReqSearchListVO;
import com.mx.server.framework.model.entity.ParamEntity;

import java.util.List;

public interface ParamService {
    List<ParamEntity> getParamList(ReqSearchListVO req);

    void upsertParam(ParamEntity paramEntity);

    void deleteParam(ReqDeleteVO reqDeleteVO);

    ParamEntity getParam(Long id, String code);
}
