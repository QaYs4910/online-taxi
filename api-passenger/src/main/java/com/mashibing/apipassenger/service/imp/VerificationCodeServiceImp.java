package com.mashibing.apipassenger.service.imp;

import com.mashibing.apipassenger.reomte.ServiceVerificationCodeClient;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServiceImp  implements VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;
    @Override
   public String generatorCode(String passengerPhone){
        ResponseResult<NumberCodeResponse> response = this.serviceVerificationCodeClient.getNumberCode();
        //调用验证码服务,获取验证码
        System.out.println("remote number code:"+response.getData().getNumberCode());
        System.out.println("调用验证码服务,获取验证码");
        System.out.println(passengerPhone);
        //存入redis
        System.out.println("存入redis");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",1);
        jsonObject.put("message","success");
        return jsonObject.toString();
    }


}
