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


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private int statusCode;

        private String message;

        private CurrencyCategory currencyCategory;

        private List<CurrencyCategory> currencyCategoryList;

        private Currentprice currentprice;

        private String updateTimeString;

        private List<CurrencyInformation> currencyInformation;


        public Builder statusCode(int statusCode){
            this.statusCode = statusCode;
            return this;
        }

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Builder currencyCategory(CurrencyCategory currencyCategory){
            this.currencyCategory = currencyCategory;
            return this;
        }

        public Builder currencyCategoryList(List<CurrencyCategory> currencyCategoryList){
            this.currencyCategoryList = currencyCategoryList;
            return this;
        }

        public Builder currentprice(Currentprice currentprice){
            this.currentprice = currentprice;
            return this;
        }

        public Builder updateTimeString(String updateTimeString){
            this.updateTimeString = updateTimeString;
            return this;
        }

        public Builder currencyInformation(List<CurrencyInformation> currencyInformation){
            this.currencyInformation = currencyInformation;
            return this;
        }

        public Response build(){
            return new Response(this);
        }
    }

    public Response(Builder builder){
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.currencyCategory = builder.currencyCategory;
        this.currencyCategoryList = builder.currencyCategoryList;
        this.currentprice = builder.currentprice;
        this.updateTimeString = builder.updateTimeString;
        this.currencyInformation = builder.currencyInformation;
    }
}
