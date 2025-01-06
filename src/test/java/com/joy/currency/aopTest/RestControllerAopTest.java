package com.joy.currency.aopTest;

import com.joy.currency.aop.RestControllerAop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import nl.altindag.log.LogCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestControllerAopTest {
    @InjectMocks
    private RestControllerAop restControllerAop;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Signature signature;

    private static LogCaptor logCaptor;

    @BeforeAll
    static void setupLogCaptor(){
        logCaptor = LogCaptor.forClass(RestControllerAop.class);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void clearLogs() {
        logCaptor.clearLogs();
    }

    @AfterAll
    public static void tearDown() {
        logCaptor.close();
    }

    @Test
    void test_logAround() throws Throwable {
        // 模擬 HttpServletRequest
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setRequestURI("/test/api");
        mockRequest.setMethod("GET");
        mockRequest.setParameter("param1", "value1");

        // 將 Mock Request 綁定到當前請求上下文
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));

        // 模擬 JoinPoint
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethod");
        when(joinPoint.proceed()).thenReturn(ResponseEntity.ok().body("uccz7j712Qs3exVpWyNkPEPwyvPYx9SiCYITuQms5JHCqvCE5M3Avq1DmDyz7V/Jxr+4C9H7wC4tVZaLnAHR3RAOxy9gS4m7tifgW5fnOAwnySbj3wcOgWHLstyerhwIwD/Ubj00sw24KFi2PgzaJpHR6eBOOiohEFf4yCsGojHm3fxv0GgtA3hDjg8QMBVptM0u0kdHuvXhvzFc7+EWtY1hQiEz4MzgEZ6AsUmsJUvQs26XQpUBkf8rT3+i/Q6c1a7VojUqlbXkP4dk/6sSeRqkYhRkKQNNNY1+rSZ70Wo="));

        // 執行切面方法
        restControllerAop.logAround(joinPoint);

        // 驗證 JoinPoint 的方法被執行
        verify(joinPoint, times(1)).proceed();

        // 驗證日誌內容
        assertTrue(logCaptor.getInfoLogs().contains("Request: GET http://localhost/test/api"));
        assertTrue(logCaptor.getInfoLogs().contains("Entering method: testMethod"));
    }


}
