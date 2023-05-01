package com.mx.server.framework.service.impl;

import com.github.pagehelper.PageHelper;
import com.mx.server.framework.dao.ParamMapper;
import com.mx.server.framework.model.vo.ReqDeleteVO;
import com.mx.server.framework.model.vo.ReqSearchListVO;
import com.mx.server.framework.model.entity.ParamEntity;
import com.mx.server.framework.service.ParamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParamServiceImpl implements ParamService {
    private final ParamMapper paramMapper;

    @Override
    public List<ParamEntity> getParamList(ReqSearchListVO req) {
        PageHelper.startPage(req.getPage(), req.getSize());
        return paramMapper.selectParamList(req);
    }

    @Override
    public void upsertParam(ParamEntity paramEntity) {
        if (null != paramEntity) {
            ParamEntity dbParam = paramMapper.selectById(paramEntity.getId());
            if (null != dbParam) {
                paramMapper.updateById(paramEntity);
            } else {
                paramMapper.insert(paramEntity);
            }
        }
    }

    @Override
    public void deleteParam(ReqDeleteVO reqDeleteVO) {
        paramMapper.physicalDeleteById(reqDeleteVO.getIds().get(0));
    }
}
