package com.mx.server.web.service.impl;

import com.mx.server.web.dao.DemoDao;
import com.mx.server.web.model.entity.DemoEntity;
import com.mx.server.web.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Maoxian
 * @since 2023/4/9
 */
@Service
@RequiredArgsConstructor
public class DemoServiceImpl implements DemoService {
    private final DemoDao demoDao;
    @Override
    public DemoEntity getFromDb() {
        return demoDao.selectOne();
    }
}
