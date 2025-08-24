package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;


public interface UserService {

    ResponseResult loginOrRegister(String passengerPhone);
}
