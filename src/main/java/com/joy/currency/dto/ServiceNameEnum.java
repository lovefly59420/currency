package com.joy.currency.dto;

import lombok.Getter;

public enum ServiceNameEnum {

    CURRENCY_CATEGORY_SERVICE("CurrencyCategoryService"),
    COINDESK_SERVICE("CoindeskService"),
    ;

    @Getter
    private String name;

    ServiceNameEnum(String name){
        this.name = name;
    }
}
