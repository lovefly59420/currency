package com.joy.currency.aop;

import com.joy.currency.dto.Response;
import com.joy.currency.util.AESUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class RestControllerAop {

    @Pointcut("execution(public * com.joy.currency.controller.CurrencyCategoryController.*(..))")
    public void pointcutCurrencyCategoryController(){
    }


    @Around("execution(public * com.joy.currency.controller..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("Request: {} {}",request.getMethod(),request.getRequestURL());

        String methodName = joinPoint.getSignature().getName();
        log.info("Entering method: {}", methodName);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info("Exiting method: {} (execution time: {}ms)", methodName, executionTime);

        ResponseEntity<String> response = (ResponseEntity<String>)result;
        log.info("Response body: {}", AESUtil.getInstance().decryptResponseBody(response.getBody()));
        return result;
    }
}
