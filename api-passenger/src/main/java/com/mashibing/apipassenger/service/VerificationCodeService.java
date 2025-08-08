package com.mashibing.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

   public String generatorCode(String passengerPhone){
        //调用验证码服务,获取验证码
        String code = "1111";
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
