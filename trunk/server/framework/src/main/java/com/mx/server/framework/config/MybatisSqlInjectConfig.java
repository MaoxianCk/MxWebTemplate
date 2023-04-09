package com.mx.server.framework.config;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisSqlInjectConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, now);

        Integer loginId = null;
        try {
            if (StpUtil.isLogin()) {
                loginId = StpUtil.getLoginIdAsInt();
            }
        } catch (SaTokenException ignore){}
        this.strictInsertFill(metaObject, "createUserId", Integer.class, loginId);
        this.strictInsertFill(metaObject, "updateUserId", Integer.class, loginId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        Integer loginId = null;
        if (StpUtil.isLogin()) {
            loginId = StpUtil.getLoginIdAsInt();
        }
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateUserId", Integer.class, loginId);
    }
}
