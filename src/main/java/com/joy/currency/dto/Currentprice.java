package com.joy.currency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class Currentprice {
    private TimeTemplate time;
    private String disclaimer;
    private String chartName;
    private Map<String, BpiDetail> bpi;
}
