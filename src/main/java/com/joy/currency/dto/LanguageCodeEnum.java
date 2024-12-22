package com.joy.currency.dto;

import lombok.Getter;

public enum LanguageCodeEnum {

    ZH_TW("zh-TW"),
    JA_JP("ja-JP"),
    EN_US("en-US"),
    ZH_CN("zh-CN"),
    ;

    @Getter
    private String languageCodeString;

    LanguageCodeEnum(String languageCodeString){
        this.languageCodeString = languageCodeString;
    }
}
