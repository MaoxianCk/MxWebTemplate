package com.mx.server.framework.config.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.mx.server.framework.config.mybatis.methods.PhysicalDeleteByBatchIds;
import com.mx.server.framework.config.mybatis.methods.PhysicalDeleteById;

import java.util.List;

public class MxLogicSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new PhysicalDeleteById("physicalDeleteById"));
        methodList.add(new PhysicalDeleteByBatchIds("physicalDeleteByBatchIds"));
        return methodList;
    }
}