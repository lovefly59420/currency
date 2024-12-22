package com.joy.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BpiDetail {
    private String code;
    private String symbol;
    private String rate;
    private String description;
    private Float rate_float;
}
