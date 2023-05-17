package com.mx.server.framework.service.impl;

import com.github.pagehelper.PageHelper;
import com.mx.server.framework.components.RedisCache;
import com.mx.server.framework.dao.DictMapper;
import com.mx.server.framework.error.BusinessException;
import com.mx.server.framework.error.EmBusinessErr;
import com.mx.server.framework.model.entity.DictEntity;
import com.mx.server.framework.model.vo.req.ReqDeleteVO;
import com.mx.server.framework.model.vo.req.ReqDictSearchVO;
import com.mx.server.framework.model.vo.res.ResTreeNodeVO;
import com.mx.server.framework.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Maoxian
 * @since 2023/4/29
 */
@Service
@RequiredArgsConstructor

public class DictServiceImpl implements DictService {
    private final DictMapper dictMapper;
    private final RedisCache redisCache;
    @Override
    public List<DictEntity> getDictList(ReqDictSearchVO req){
        PageHelper.startPage(req.getPage(), req.getSize());
        return dictMapper.selectDictList(req);
    }
    @Override
    public List<ResTreeNodeVO> getDictTree(){
        List<ResTreeNodeVO> list=dictMapper.selectDictTreePNode(null);
        for (ResTreeNodeVO node : list){
            node.setChildren(dictMapper.selectDictTreeNode(node.getId()));
        }
        return list;
    }
    @Override
    public void upsertDict(DictEntity dictEntity) {
        if (null != dictEntity) {
            DictEntity dbDict = dictMapper.selectById(dictEntity.getId());

            checkExisted(dictEntity.getCode(), dictEntity.getId());

            int changes;
            if (null != dbDict) {
                changes = dictMapper.updateById(dictEntity);
            } else {
                changes = dictMapper.insert(dictEntity);
            }
            if (changes > 0) {
//                refreshCache();
            }
        }
    }
    @Override
    public void deleteDict(ReqDeleteVO reqDeleteVO) {
        int changes = dictMapper.physicalDeleteByBatchIds(reqDeleteVO.getIds());
        if (changes > 0) {
//            refreshCache();
        }
    }
    private void checkExisted(String code, Long ignoreId) {
        DictEntity dbDict= dictMapper.selectByIdOrCode(null, code);
        if (null != dbDict && !dbDict.getId().equals(ignoreId)) {
            throw new BusinessException(EmBusinessErr.DICT_ALREADY_EXISTED);
        }
    }

//    private void refreshCache() {
//        List<DictEntity> list = dictMapper.selectDictList(null);
//        Map<String, ParamCacheMO> map = list.stream().collect(Collectors.toMap(DictEntity::getCode, DictCacheMO::new));
//        redisCache.setCacheMap(RedisConstants.PARAM_MAP_KEY, map);
////        log.info("refresh dict cache");
//    }
}
