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

import java.util.*;

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
        //查询所有的字典
        List<DictEntity> list=dictMapper.selectList(null);
        // 创建一个HashMap来存储父子关系
        Map<Long, List<DictEntity>> dictMap = new HashMap<>();
        for (DictEntity dict : list){
            Long parentId=dict.getParentId();
            //若为根节点
            if(parentId==null){
                // 根节点使用0L占位符
                parentId = 0L;
            }
            //根据指定的键（parentId）来获取对应的值
            // 如果该键在 dictMap 中不存在，则会执行提供的函数来生成新值并放入 dictMap 中
            dictMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(dict);
        }
        // 创建一个新的列表来存储结果树结构
        List<ResTreeNodeVO> resList = new LinkedList<>();
        // 构建树结构
        buildTree(dictMap, resList, 0L);
        //返回的结果
        return resList;
    }
    private void buildTree(Map<Long, List<DictEntity>> dictMap, List<ResTreeNodeVO> resList, Long parentId) {
        List<DictEntity> children = dictMap.get(parentId);
        if (children != null) {
            for (DictEntity dict : children) {
                ResTreeNodeVO node = transformEntity2TreeNode(dict);
                resList.add(node);
                buildTree(dictMap, node.getChildren(), dict.getId());
            }
        }
    }
    private ResTreeNodeVO transformEntity2TreeNode(DictEntity dict){
        ResTreeNodeVO treeNodeV0 = new ResTreeNodeVO();
        treeNodeV0.setId(dict.getId());
        treeNodeV0.setLabel(dict.getLabel());
        treeNodeV0.setValue(dict.getValue());
        treeNodeV0.setCode(dict.getCode());
        treeNodeV0.setChildren(new LinkedList<>());
        return treeNodeV0;
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
