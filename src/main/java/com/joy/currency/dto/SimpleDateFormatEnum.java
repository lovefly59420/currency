package com.joy.currency.dto;

import lombok.Getter;

public enum SimpleDateFormatEnum {
    YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss"),
    ;

    @Getter
    private String dateTimeFormatString;

    SimpleDateFormatEnum(String dateTimeFormatString){
        this.dateTimeFormatString = dateTimeFormatString;
    }
}
