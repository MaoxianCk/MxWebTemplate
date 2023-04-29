package com.mx.server.framework.service.impl;

import com.mx.server.framework.dao.DictMapper;
import com.mx.server.framework.model.entity.DictEntity;
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

    @Override
    public List<DictEntity> getDictList() {
        return dictMapper.selectList(null);
    }
}
