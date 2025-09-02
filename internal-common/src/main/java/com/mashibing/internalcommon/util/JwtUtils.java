package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SIGN = "%$&%$%^$";
    private static final String JWT_KEY_PHONE = "phone";
    //乘客1 司机2
    private static final String JWT_KEY_IDENTITY = "identity";

    public static String generatorToken(String passengerPhone,String identity){

        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,passengerPhone);
        map.put(JWT_KEY_IDENTITY,identity);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();
        JWTCreator.Builder builder = JWT.create();
        //整合map
        map.forEach(
                (k,v)->{
            builder.withClaim(k,v);
        });
        //整合过期时间
        builder.withExpiresAt(date);
        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    //解析token
    public static TokenResult paresToken(String token){

        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).toString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).toString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;

    }
    public static void main(String[] args) {

        String s = JwtUtils.generatorToken("17521209671","1");
        System.out.println("生成token:"+s);
        TokenResult s1 = JwtUtils.paresToken(s);
        System.out.println("解析token:__------------");
        System.out.println("手机号:"+s1.getPhone()+",身份:"+s1.getIdentity());
    }
}
