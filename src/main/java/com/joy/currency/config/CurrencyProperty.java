package com.joy.currency.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrencyProperty{

    @Getter
    @Value("${spring.messages.basename}")
    private String messagesBasename;
}
