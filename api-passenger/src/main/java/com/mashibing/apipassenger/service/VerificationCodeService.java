package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface VerificationCodeService {

    ResponseResult generatorCode(String passengerPhone);
}
