package com.mx.server.framework.service;


import com.mx.server.framework.model.vo.req.ReqLoginVO;
import com.mx.server.framework.model.vo.req.ReqRegisterVO;
import com.mx.server.framework.model.vo.res.ResLoginVO;

public interface UserService {
    ResLoginVO doLogin(ReqLoginVO loginVO);

    void doRegister(ReqRegisterVO registerVO);
}
