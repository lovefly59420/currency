package com.joy.currency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TimeTemplate {
    private String updated;
    private Date updatedISO;
    private String updateduk;
}
