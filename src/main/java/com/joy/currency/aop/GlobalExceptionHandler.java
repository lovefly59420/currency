package com.joy.currency.aop;

import com.joy.currency.dto.ErrorCodeEnum;
import com.joy.currency.dto.Response;
import com.joy.currency.exception.CurrencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CurrencyException.class)
    public ResponseEntity<Response> handleCurrencyException(Exception e) {
        // 創建自定義錯誤響應
        Response response = new Response();
        response.setStatusCode(ErrorCodeEnum.CURRENCY_EXCEPTION.getErrorCode());
        response.setMessage(e.getMessage());

        // 記錄異常信息
        log.error("An error occurred: {}", e.getMessage(), e);

        // 返回通用錯誤消息
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        // 創建自定義錯誤響應
        Response response = new Response();
        response.setStatusCode(ErrorCodeEnum.EXCEPTION.getErrorCode());
        response.setMessage(e.getMessage());

        // 記錄異常信息
        log.error("An error occurred: {}", e.getMessage(), e);

        // 返回通用錯誤消息
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
