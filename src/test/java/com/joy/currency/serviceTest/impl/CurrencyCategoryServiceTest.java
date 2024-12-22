package com.joy.currency.serviceTest.impl;

import com.joy.currency.dto.ErrorCodeEnum;
import com.joy.currency.dto.LanguageCodeEnum;
import com.joy.currency.dto.MessageEnum;
import com.joy.currency.dto.Response;
import com.joy.currency.entity.CurrencyCategory;
import com.joy.currency.repository.CurrencyCategoryRepository;
import com.joy.currency.service.impl.CurrencyCategoryService;
import com.joy.currency.util.I18nUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyCategoryServiceTest {

    @Mock
    private CurrencyCategoryRepository currencyCategoryRepository;

    @InjectMocks
    private CurrencyCategoryService currencyCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 手動設定 I18nUtil 的靜態變數
        I18nUtil.setBasename("messages");
    }

    @Test
    public void test_findAll() throws Exception {
        List<CurrencyCategory> currencyCategorys = Lists.list(
                new CurrencyCategory("USD","美金", new Date()),
                new CurrencyCategory("EUR","歐元", new Date()),
                new CurrencyCategory("GBP","英鎊", new Date()));
        List<CurrencyCategory> sortedCurrencyCategorys = Lists.list(
                new CurrencyCategory("EUR","歐元", new Date()),
                new CurrencyCategory("GBP","英鎊", new Date()),
                new CurrencyCategory("USD","美金", new Date()));
        Response mockResponse = new Response();
        mockResponse.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
        mockResponse.setMessage(MessageEnum.FIND_ALL_SUCCESS.getMessage());
        mockResponse.setCurrencyCategoryList(sortedCurrencyCategorys);

        when(currencyCategoryRepository.findAll()).thenReturn(currencyCategorys);
        Response response = currencyCategoryService.findAll(LanguageCodeEnum.ZH_TW.getLanguageCodeString());
        assertEquals(mockResponse.getCurrencyCategoryList().get(0).getCurrency(),response.getCurrencyCategoryList().get(0).getCurrency());
        assertEquals(mockResponse.getCurrencyCategoryList().get(1).getCurrency(),response.getCurrencyCategoryList().get(1).getCurrency());
        assertEquals(mockResponse.getCurrencyCategoryList().get(2).getCurrency(),response.getCurrencyCategoryList().get(2).getCurrency());
    }

    @Test
    public void test_findByCurrency(){
        List<CurrencyCategory> currencyCategorys = Lists.list(
                new CurrencyCategory("USD","美金", new Date()),
                new CurrencyCategory("EUR","歐元", new Date()),
                new CurrencyCategory("GBP","英鎊", new Date()));
        Response mockResponse = new Response();
        mockResponse.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
        mockResponse.setMessage(MessageEnum.FIND_BY_SUCCESS.getMessage());
        mockResponse.setCurrencyCategory(currencyCategorys.get(0));

        when(currencyCategoryRepository.findByCurrency("USD")).thenReturn(Optional.of(currencyCategorys.get(0)));
        Response response = currencyCategoryService.findByCurrency("USD",LanguageCodeEnum.ZH_TW.getLanguageCodeString());
        assertEquals(mockResponse,response);
    }

    @Test
    public void test_saveAll(){
        List<CurrencyCategory> currencyCategorys = Lists.list(
                new CurrencyCategory("USD","美金", new Date()),
                new CurrencyCategory("EUR","歐元", new Date()),
                new CurrencyCategory("GBP","英鎊", new Date()));
        Response mockResponse = new Response();
        mockResponse.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
        mockResponse.setMessage(MessageEnum.SAVE_ALL_SUCCESS.getMessage());
        mockResponse.setCurrencyCategoryList(currencyCategorys);

        when(currencyCategoryRepository.saveAll(currencyCategorys)).thenReturn(currencyCategorys);
        Response response = currencyCategoryService.saveAll(currencyCategorys);
        assertEquals(mockResponse,response);
    }

    @Test
    public void test_update(){
        CurrencyCategory currencyCategory = new CurrencyCategory(99L,"USD","美金", new Date(),null,null);
        CurrencyCategory updateCurrencyCategory = new CurrencyCategory(99L,"USD","美金_update", new Date(),new Date(),null);
        Response mockResponse = new Response();
        mockResponse.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
        mockResponse.setMessage(MessageEnum.UPDATE_SUCCESS.getMessage());
        mockResponse.setCurrencyCategory(updateCurrencyCategory);

        when(currencyCategoryRepository.findByCurrency("USD")).thenReturn(Optional.of(currencyCategory));
        given(currencyCategoryRepository.save(any(CurrencyCategory.class))).willReturn(updateCurrencyCategory);
        Response response = currencyCategoryService.update("USD","美金_update");
        assertEquals(mockResponse,response);
    }

    @Test
    public void test_delete(){
        CurrencyCategory currencyCategory = new CurrencyCategory("USD","美金", new Date());
        Response mockResponse = new Response();
        mockResponse.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
        mockResponse.setMessage(MessageEnum.DELETE_SUCCESS.getMessage());
        mockResponse.setCurrencyCategory(currencyCategory);

        when(currencyCategoryRepository.findByCurrency("USD")).thenReturn(Optional.of(currencyCategory));
        Response response = currencyCategoryService.delete("USD");
        assertEquals(mockResponse,response);
    }

    @Test
    public void test_deleteAll(){
        Response response = currencyCategoryService.deleteAll();
        assertEquals(ErrorCodeEnum.OK.getErrorCode(),response.getStatusCode());
    }
}
