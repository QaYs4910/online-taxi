package com.mashibing.apipassenger.service.imp;

import com.mashibing.apipassenger.reomte.ServiceVerificationCodeClient;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeServiceImp  implements VerificationCodeService {

    //乘客验证码前缀
    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public ResponseResult generatorCode(String passengerPhone){
        ResponseResult<NumberCodeResponse> response = this.serviceVerificationCodeClient.getNumberCode(6);

        //调用验证码服务,获取验证码
        int numberCode = response.getData().getNumberCode();

        //存入redis
        //前缀+手机号
        String key = verificationCodePrefix + passengerPhone;
        //设置key value 值的过期时间为2分钟
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        //短信服务接通

        return ResponseResult.success("");
    }


}
