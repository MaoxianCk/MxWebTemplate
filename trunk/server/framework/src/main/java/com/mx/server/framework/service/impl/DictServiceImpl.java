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

import java.util.LinkedList;
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
        //查询所有的字典
        List<DictEntity> list=dictMapper.selectDictList(null);
        //返回的结果
        List<ResTreeNodeVO> resList=new LinkedList<>();
        for (DictEntity dict : list){
            //若为根节点
            if(dict.getParentId()==null){
                //类型转换成节点类型
                ResTreeNodeVO rnode=transformEntity2TreeNode(dict);
                //在树结构里添加根
                resList.add(rnode);
            }
        }
        for (DictEntity dict : list){
            if(dict.getParentId()!=null){
                //查找对应父级
                addToParent(resList, dict);
            }
        }
        return resList;
    }
    private void addToParent(List<ResTreeNodeVO> resList, DictEntity dict) {
        for (ResTreeNodeVO rnode : resList) {
            if (rnode.getId().equals(dict.getParentId())) {
                //类型转换成节点类型
                ResTreeNodeVO node=transformEntity2TreeNode(dict);
                rnode.getChildren().add(node);
                return;
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

    public void deletet() {

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
