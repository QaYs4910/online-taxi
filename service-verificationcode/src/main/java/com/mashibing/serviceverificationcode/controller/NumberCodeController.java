package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {


    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){

        System.out.println("size:"+size);
        int result = (int)((Math.random()*9+ 1) * (Math.pow(10,size - 1)));
        NumberCodeResponse codeResponse = new NumberCodeResponse();
        codeResponse.setNumberCode(result);
        System.out.println("service code:"+result);
        return  ResponseResult.success(codeResponse);
    }
}
