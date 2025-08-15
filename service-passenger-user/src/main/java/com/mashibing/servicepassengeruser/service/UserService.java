package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    ResponseResult loginOrRegister(String passengerPhone);
}
