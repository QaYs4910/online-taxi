package com.mashibing.servicepassengeruser.service.imp;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;
    @Override
    public ResponseResult loginOrRegister(String passengerPhone) {
        /**
         * 1.根据手机号查询用户
         * 2.查询用户是否存在
         * 3.如果不存在,插入用户信息
         */
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = this.passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers==null?"无记录":passengerUsers.get(0).getPassengerName());
        System.out.println("UserService 被调用>手机号:"+passengerPhone);
        return ResponseResult.success();
    }

}
