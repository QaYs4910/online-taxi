package com.mashibing.internalcommon.dto;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) //链式调用
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    /**
     * 默认无参数返回成功
     * @return
     */
    public static  ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue());
    }
    /**
     * 成功响应方法
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult success( T data){

        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).
                setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    /**
     * 统一的失败默认
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult fail(T data){
        return new ResponseResult().setData(data);
    }
    /**
     * 失败：自定义错误码和提示
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult fail(int code,String message){
        return  new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     * 失败: 自定义失败,错误码,错误信息,具体错误
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(int code,String message,String data){
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

}
