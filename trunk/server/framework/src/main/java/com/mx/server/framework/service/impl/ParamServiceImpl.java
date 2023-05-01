package com.mx.server.framework.service.impl;

import com.github.pagehelper.PageHelper;
import com.mx.server.framework.components.RedisCache;
import com.mx.server.framework.constant.RedisConstants;
import com.mx.server.framework.dao.ParamMapper;
import com.mx.server.framework.error.BusinessException;
import com.mx.server.framework.error.EmBusinessErr;
import com.mx.server.framework.model.entity.ParamEntity;
import com.mx.server.framework.model.mo.ParamCacheMO;
import com.mx.server.framework.model.vo.ReqDeleteVO;
import com.mx.server.framework.model.vo.ReqSearchListVO;
import com.mx.server.framework.service.ParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParamServiceImpl implements ParamService {
    private final ParamMapper paramMapper;
    private final RedisCache redisCache;

    @Override
    public List<ParamEntity> getParamList(ReqSearchListVO req) {
        PageHelper.startPage(req.getPage(), req.getSize());
        List<ParamEntity> list = paramMapper.selectParamList(req);
        return list;
    }

    @Override
    public void upsertParam(ParamEntity paramEntity) {
        if (null != paramEntity) {
            ParamEntity dbParam = paramMapper.selectById(paramEntity.getId());

            checkExisted(paramEntity.getCode(), paramEntity.getId());

            int changes;
            if (null != dbParam) {
                changes = paramMapper.updateById(paramEntity);
            } else {
                changes = paramMapper.insert(paramEntity);
            }
            if (changes > 0){
                refreshCache();
            }
        }
    }

    @Override
    public void deleteParam(ReqDeleteVO reqDeleteVO) {
        int changes = paramMapper.physicalDeleteByBatchIds(reqDeleteVO.getIds());
        if (changes > 0){
            refreshCache();
        }
    }

    @Override
    public ParamEntity getParam(Long id, String code) {
        if (null == id && null == code) {
            return null;
        }

        return paramMapper.selectByIdOrCode(id, code);
    }

    private void checkExisted(String code, Long ignoreId) {
        ParamEntity dbParam = paramMapper.selectByIdOrCode(null, code);
        if (null != dbParam && !dbParam.getId().equals(ignoreId)) {
            throw new BusinessException(EmBusinessErr.PARAM_ALREADY_EXISTED);
        }
    }

    private void refreshCache() {
        List<ParamEntity> list = paramMapper.selectList(null);
        Map<String, ParamCacheMO> map = list.stream().collect(Collectors.toMap(ParamEntity::getCode, ParamCacheMO::new));
        redisCache.setCacheMap(RedisConstants.PARAM_MAP_KEY, map);
        log.info("refresh param cache");
    }
}
