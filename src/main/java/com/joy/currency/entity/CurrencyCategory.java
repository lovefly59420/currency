package com.joy.currency.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "currency_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @Column(nullable = false, unique = true)
    private String currency;

    @Column(nullable = false)
    private String currencyChineseName;

    @Column(nullable = false)
    private Date createTime;

    private Date updateTime;

    private Float rate_float;

    public CurrencyCategory(String currency, String currencyChineseName, Date createTime){
        this.currency = currency;
        this.currencyChineseName = currencyChineseName;
        this.createTime = createTime;
    }
}
