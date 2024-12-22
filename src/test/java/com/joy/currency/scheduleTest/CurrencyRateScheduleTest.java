package com.joy.currency.scheduleTest;

import com.joy.currency.dto.*;
import com.joy.currency.entity.CurrencyCategory;
import com.joy.currency.schedule.CurrencyRateSchedule;
import com.joy.currency.service.factory.AbstractFactory;
import com.joy.currency.service.interfac.ICoindeskService;
import com.joy.currency.service.interfac.ICurrencyCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateScheduleTest {
    @InjectMocks
    private CurrencyRateSchedule currencyRateSchedule;

    @Mock
    private AbstractFactory currencyCategoryServiceFactory;

    @Mock
    private AbstractFactory coindeskServiceFactory;

    @Mock
    private ICoindeskService coindeskService;

    @Mock
    private ICurrencyCategoryService currencyCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_syncCurrencyRate_NoDataFromApi() {
        // 模擬 coindeskService 返回的空數據
        when(coindeskServiceFactory.getICoindeskService(ServiceNameEnum.COINDESK_SERVICE.getName()))
                .thenReturn(coindeskService);
        Currentprice currentprice = new Currentprice();
        currentprice.setBpi(Collections.emptyMap());
        when(coindeskService.getCurrentpriceFromApi()).thenReturn(currentprice);

        // 執行測試方法
        currencyRateSchedule.syncCurrencyRate();

        // 驗證後續方法未被調用
        verify(currencyCategoryServiceFactory, never()).getICurrencyCategoryService(anyString());
    }

    @Test
    void test_syncCurrencyRate_WithValidData() {
        // 模擬 coindeskService 返回有效數據
        when(coindeskServiceFactory.getICoindeskService(ServiceNameEnum.COINDESK_SERVICE.getName()))
                .thenReturn(coindeskService);
        Map<String, BpiDetail> bpiMap = new HashMap<>();
        BpiDetail detail = new BpiDetail();
        detail.setRate_float(1.23f);
        bpiMap.put("USD", detail);
        Currentprice currentprice = new Currentprice();
        currentprice.setBpi(bpiMap);
        when(coindeskService.getCurrentpriceFromApi()).thenReturn(currentprice);

        // 模擬 currencyCategoryService 行為
        when(currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName()))
                .thenReturn(currencyCategoryService);
        Response response = new Response();
        response.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
        CurrencyCategory category = new CurrencyCategory();
        category.setCurrency("USD");
        response.setCurrencyCategory(category);
        when(currencyCategoryService.findByCurrency(eq("USD"), any())).thenReturn(response);

        // 執行測試方法
        currencyRateSchedule.syncCurrencyRate();

        // 驗證相關方法被正確調用
        verify(currencyCategoryService, times(1)).findByCurrency(eq("USD"), any());
        verify(currencyCategoryService, times(1)).saveAll(anyList());
    }

    @Test
    void test_getCurrencyRateMap() {
        // 測試數據
        Map<String, BpiDetail> bpiMap = new HashMap<>();
        BpiDetail detail1 = new BpiDetail();
        detail1.setRate_float(1.23f);
        bpiMap.put("USD", detail1);
        BpiDetail detail2 = new BpiDetail();
        detail2.setRate_float(Float.NaN);
        bpiMap.put("EUR", detail2);

        // 執行方法
        Map<String, Float> result = currencyRateSchedule.getCurrencyRateMap(bpiMap);

        // 驗證結果
        assertEquals(1, result.size());
        assertTrue(result.containsKey("USD"));
        assertEquals(1.23f, result.get("USD"));
    }

    @Test
    void test_getUpdateCurrencyCategory() {
        // 測試數據
        Map<String, Float> currencyRateMap = Map.of("USD", 1.23f);
        when(currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName()))
                .thenReturn(currencyCategoryService);

        Response response = new Response();
        response.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
        CurrencyCategory category = new CurrencyCategory();
        category.setCurrency("USD");
        response.setCurrencyCategory(category);
        when(currencyCategoryService.findByCurrency(eq("USD"), any())).thenReturn(response);

        // 執行方法
        List<CurrencyCategory> result = currencyRateSchedule.getUpdateCurrencyCategory(currencyRateMap);

        // 驗證結果
        assertEquals(1, result.size());
        assertEquals("USD", result.get(0).getCurrency());
        assertEquals(1.23f, result.get(0).getRate_float());
    }
}
