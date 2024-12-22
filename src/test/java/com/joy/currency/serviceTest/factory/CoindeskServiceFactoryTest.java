package com.joy.currency.serviceTest.factory;

import com.joy.currency.dto.ServiceNameEnum;
import com.joy.currency.service.factory.CoindeskServiceFactory;
import com.joy.currency.service.interfac.ICoindeskService;
import com.joy.currency.service.interfac.ICurrencyCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CoindeskServiceFactoryTest {
    @Mock
    private ICoindeskService mockCoindeskService;

    @InjectMocks
    private CoindeskServiceFactory coindeskServiceFactory;

    @BeforeEach
    void setUp() {
        // 初始化 Mockito 模擬對象
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_getICurrencyCategoryService_validServiceName() {
        // 測試傳入正確的 serviceClassName
        String validServiceName = ServiceNameEnum.COINDESK_SERVICE.getName();

        // 驗證是否返回 Mock 的服務
        ICoindeskService result = coindeskServiceFactory.getICoindeskService(validServiceName);
        assertNotNull(result);
        assertEquals(mockCoindeskService, result);
    }

    @Test
    void test_getICurrencyCategoryService_invalidServiceName() {
        // 測試傳入無效的 serviceClassName
        String invalidServiceName = "INVALID_SERVICE";

        // 測試是否正確拋出異常
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            coindeskServiceFactory.getICoindeskService(invalidServiceName);
        });

        // 驗證異常訊息
        assertEquals("No such service type: " + invalidServiceName, exception.getMessage());
    }

    @Test
    void test_getICoindeskService_alwaysNull() {
        // 測試 getICoindeskService 方法返回 null 的行為
        ICurrencyCategoryService result = coindeskServiceFactory.getICurrencyCategoryService("ANY_SERVICE");
        assertNull(result);
    }
}
