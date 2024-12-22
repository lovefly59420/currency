package com.joy.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyInformation {
    private String currency;
    private String currencyChineseName;
    private String rate;

    public CurrencyInformation(){
        this.currencyChineseName = "DB No Data";
    }

}
