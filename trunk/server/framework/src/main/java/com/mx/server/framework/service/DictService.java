package com.mx.server.framework.service;

import com.mx.server.framework.model.entity.DictEntity;
import com.mx.server.framework.model.vo.req.ReqDeleteVO;
import com.mx.server.framework.model.vo.req.ReqDictSearchVO;
import com.mx.server.framework.model.vo.res.ResTreeNodeVO;

import java.util.List;

/**
 * @author Maoxian
 * @since 2023/4/29
 */
public interface DictService {
    List<DictEntity> getDictList(ReqDictSearchVO req);
    List<ResTreeNodeVO> getDictTree();
    void upsertDict(DictEntity dictEntity);

    void deleteDict(ReqDeleteVO reqDeleteVO);
}
