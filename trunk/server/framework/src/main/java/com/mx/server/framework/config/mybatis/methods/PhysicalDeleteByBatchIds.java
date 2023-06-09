package com.mx.server.framework.config.mybatis.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class PhysicalDeleteByBatchIds extends AbstractMethod {

    public PhysicalDeleteByBatchIds(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                SqlScriptUtils.convertForeach(
                        SqlScriptUtils.convertChoose("@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(item.getClass())",
                                "#{item}", "#{item." + tableInfo.getKeyProperty() + "}"),
                        COLL, null, "item", COMMA));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
        return this.addDeleteMappedStatement(mapperClass, methodName, sqlSource);
    }
}