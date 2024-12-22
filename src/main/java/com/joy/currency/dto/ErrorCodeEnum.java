package com.joy.currency.dto;

import lombok.Getter;

public enum ErrorCodeEnum {
    OK(200),
    CURRENCY_EXCEPTION(404),
    EXCEPTION(500),
    ;

    @Getter
    private int errorCode;

    ErrorCodeEnum(int errorCode){
        this.errorCode = errorCode;
    }
}

