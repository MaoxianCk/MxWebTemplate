package com.mx.server.framework.service.impl;

import com.mx.server.framework.dao.UserInfoMapper;
import com.mx.server.framework.dao.UserMapper;
import com.mx.server.framework.error.BusinessException;
import com.mx.server.framework.error.EmBusinessErr;
import com.mx.server.framework.model.entity.UserEntity;
import com.mx.server.framework.model.entity.UserInfoEntity;
import com.mx.server.framework.model.vo.req.ReqLoginVO;
import com.mx.server.framework.model.vo.req.ReqRegisterVO;
import com.mx.server.framework.model.vo.res.ResLoginVO;
import com.mx.server.framework.service.CommonService;
import com.mx.server.framework.service.UserService;
import com.mx.server.util.password.PasswordEnum;
import com.mx.server.util.password.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final CommonService commonService;
    @Override
    public ResLoginVO doLogin(ReqLoginVO loginVO) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doRegister(ReqRegisterVO registerVO) {
        commonService.verifyImgCaptcha(registerVO.getCaptcha());

        UserEntity userDb = userMapper.selectByUsername(registerVO.getUsername());
        if (null != userDb) {
            throw new BusinessException(EmBusinessErr.ACCOUNT_EXISTED);
        }

        String hashedPwd = PasswordUtil.getPassword(registerVO.getPwd(), PasswordEnum.B_CRYPT);

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoMapper.insert(userInfoEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserInfoId(userInfoEntity.getId());
        userEntity.setUsername(registerVO.getUsername());
        userEntity.setPwd(hashedPwd);
        userMapper.insert(userEntity);
    }
}
