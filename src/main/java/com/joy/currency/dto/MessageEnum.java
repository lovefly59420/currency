package com.joy.currency.dto;

import lombok.Getter;

public enum MessageEnum {
    CURRENCY_CATEGORY_NOT_FOUND("currency category not found."),

    FIND_ALL_SUCCESS("find all currency category success. "),
    FIND_ALL_ERROR("error find all currency category. "),

    FIND_BY_SUCCESS("find currency category success. "),
    FIND_BY_ERROR("error find currency category. "),

    SAVE_ALL_SUCCESS("save currency category success. "),
    SAVE_ALL_ERROR("error save currency category. "),

    UPDATE_SUCCESS("update currency category success. "),
    UPDATE_ERROR("error update currency category. "),

    DELETE_SUCCESS("delete currency category success. "),
    DELETE_ERROR("delete currency category. "),

    GET_COINDESK_DATA_SUCCESS("get coindesk data success. "),
    GET_COINDESK_DATA_ERROR("error get coindesk data. "),

    TRANSFORM_COINDESK_SUCCESS("transform coindesk success. "),
    TRANSFORM_COINDESK_ERROR("error transform coindesk. "),
    ;

    @Getter
    private String message;


    MessageEnum(String message){
        this.message = message;
    }
}
