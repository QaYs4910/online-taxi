package com.mashibing.servicepassengeruser.service.imp;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Override
    public ResponseResult loginOrRegister(String passengerPhone) {
        /**
         * 1.根据手机号查询用户
         * 2.查询用户是否存在
         * 3.如果不存在,插入用户信息
         */
        System.out.println("UserService 被调用>手机号:"+passengerPhone);
        return ResponseResult.success();
    }

}
