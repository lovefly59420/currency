package com.joy.currency.dto;

import lombok.Getter;

public enum CurrencyCodeEnum {

    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    MESSAGE("message")
    ;

    @Getter
    private String code;

    CurrencyCodeEnum(String code){
        this.code = code;
    }
}
