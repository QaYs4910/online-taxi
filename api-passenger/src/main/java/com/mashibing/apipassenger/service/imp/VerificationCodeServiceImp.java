package com.mashibing.apipassenger.service.imp;

import com.mashibing.apipassenger.reomte.ServicePassengerUserClient;
import com.mashibing.apipassenger.reomte.ServiceVerificationCodeClient;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String tokenPrefix = "token-";
    /**
     * 生成验证码
     * @param passengerPhone
     * @return
     */
    @Override
    public ResponseResult generatorCode(String passengerPhone){

        ResponseResult<NumberCodeResponse> response = this.serviceVerificationCodeClient.getNumberCode(6);
        //调用验证码服务,获取验证码
        int numberCode = response.getData().getNumberCode();

        //存入redis
        //前缀+手机号
        String key = generatorKey(passengerPhone);
        //设置key value 值的过期时间为2分钟
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        //短信服务接通
        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     * @param passengerPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    @Override
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {

        //1.根据手机号去redis读验证码
        System.out.println("redis读验证码");
        String key = generatorKey(passengerPhone);
        String codeRedis = this.stringRedisTemplate.opsForValue().get(key);
        System.out.println("获取redisCode:"+codeRedis);

        //2.校验验证码
        System.out.println("校验验证码");
        if(StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if(!codeRedis.trim().equals(verificationCode.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //3.判断是否有用户
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        this.servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        //4.颁发令牌
        System.out.println("颁发令牌");
        String token = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY);
        //5.存储token
        String tokenkey = generatorTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY);
        this.stringRedisTemplate.opsForValue().set(tokenkey,token,30,TimeUnit.DAYS);//token存30天

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        return ResponseResult.success(tokenResponse);
    }

    private String generatorKey(String passengerPhone){

        return verificationCodePrefix + passengerPhone;
    }

    private String generatorTokenKey(String phone,String identity){

        return  tokenPrefix + phone + "-" + identity ;
    }

}
