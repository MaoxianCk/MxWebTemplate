package com.mx.server.framework.service;

import com.mx.server.framework.model.entity.DictEntity;
import com.mx.server.framework.model.vo.ReqSearchListVO;

import java.util.List;

/**
 * @author Maoxian
 * @since 2023/4/29
 */
public interface DictService {
    List<DictEntity> getDictList(ReqSearchListVO req);
}
