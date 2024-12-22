package com.joy.currency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joy.currency.entity.CurrencyCategory;
import lombok.*;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int statusCode;

    private String message;

    private CurrencyCategory currencyCategory;

    private List<CurrencyCategory> currencyCategoryList;

    private Currentprice currentprice;

    private String updateTimeString;

    private List<CurrencyInformation> currencyInformation;
}
