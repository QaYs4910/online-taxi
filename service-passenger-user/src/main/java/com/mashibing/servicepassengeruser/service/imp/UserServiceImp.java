package com.mashibing.servicepassengeruser.service.imp;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        //用户不存在插入数据
        if(passengerUsers.size() == 0){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            this.passengerUserMapper.insert(passengerUser);
        }
        System.out.println("UserService 被调用>手机号:"+passengerPhone);
        return ResponseResult.success();
    }

}
