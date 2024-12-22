package com.joy.currency.aopTest;

import com.joy.currency.aop.GlobalExceptionHandler;
import com.joy.currency.dto.Response;
import com.joy.currency.exception.CurrencyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void test_handleCurrencyException() {
        // 模擬異常
        CurrencyException exception = new CurrencyException("Currency not found");

        // 執行測試方法
        ResponseEntity<Response> responseEntity = globalExceptionHandler.handleCurrencyException(exception);

        // 驗證響應狀態碼
        assertEquals(404, responseEntity.getStatusCodeValue());

        // 驗證響應主體
        Response responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(404, responseBody.getStatusCode());
    }

    @Test
    void test_handleGenericException() {
        // 模擬一般異常
        Exception exception = new Exception("An unexpected error occurred");

        // 執行測試方法
        ResponseEntity<Response> responseEntity = globalExceptionHandler.handleException(exception);

        // 驗證響應狀態碼
        assertEquals(500, responseEntity.getStatusCodeValue());

        // 驗證響應主體
        Response responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(500, responseBody.getStatusCode());
    }
}
