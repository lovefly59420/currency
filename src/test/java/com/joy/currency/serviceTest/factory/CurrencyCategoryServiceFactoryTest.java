package com.joy.currency.serviceTest.factory;

import com.joy.currency.dto.ServiceNameEnum;
import com.joy.currency.service.factory.CurrencyCategoryServiceFactory;
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
public class CurrencyCategoryServiceFactoryTest {

    @Mock
    private ICurrencyCategoryService mockCurrencyCategoryService;

    @InjectMocks
    private CurrencyCategoryServiceFactory currencyCategoryServiceFactory;

    @BeforeEach
    void setUp() {
        // 初始化 Mockito 模擬對象
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_getICurrencyCategoryService_validServiceName() {
        // 測試傳入正確的 serviceClassName
        String validServiceName = ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName();

        // 驗證是否返回 Mock 的服務
        ICurrencyCategoryService result = currencyCategoryServiceFactory.getICurrencyCategoryService(validServiceName);
        assertNotNull(result);
        assertEquals(mockCurrencyCategoryService, result);
    }

    @Test
    void test_getICurrencyCategoryService_invalidServiceName() {
        // 測試傳入無效的 serviceClassName
        String invalidServiceName = "INVALID_SERVICE";

        // 測試是否正確拋出異常
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyCategoryServiceFactory.getICurrencyCategoryService(invalidServiceName);
        });

        // 驗證異常訊息
        assertEquals("No such service type: " + invalidServiceName, exception.getMessage());
    }

    @Test
    void test_getICoindeskService_alwaysNull() {
        // 測試 getICoindeskService 方法返回 null 的行為
        ICoindeskService result = currencyCategoryServiceFactory.getICoindeskService("ANY_SERVICE");
        assertNull(result);
    }
}
