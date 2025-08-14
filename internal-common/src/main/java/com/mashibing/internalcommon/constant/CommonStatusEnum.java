package com.mashibing.internalcommon.constant;

import lombok.Getter;

public enum CommonStatusEnum {

    SUCCESS(1,"success"),  //成功
    FAIL(0,"fail");        //失败

    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
