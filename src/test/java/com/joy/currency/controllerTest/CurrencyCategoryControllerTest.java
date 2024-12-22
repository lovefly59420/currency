package com.joy.currency.controllerTest;

import com.joy.currency.controller.CurrencyCategoryController;
import com.joy.currency.dto.ErrorCodeEnum;
import com.joy.currency.dto.LanguageCodeEnum;
import com.joy.currency.dto.Response;
import com.joy.currency.entity.CurrencyCategory;
import com.joy.currency.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
public class CurrencyCategoryControllerTest {

    @Autowired
    private CurrencyCategoryController currencyCategoryController;

    @Test
    public void test_findByCurrency() throws Exception {
        currencyCategoryController.deleteAll();
        List<CurrencyCategory> currencyCategorys = Lists.list(
                new CurrencyCategory("USD","美金", null),
                new CurrencyCategory("EUR","歐元", null),
                new CurrencyCategory("GBP","英鎊", null));
        ResponseEntity<String> saveResponse = currencyCategoryController.addAll(AESUtil.getInstance().encrypt(currencyCategorys));
        ResponseEntity<String> findResponse = currencyCategoryController.findByCurrency("USD", LanguageCodeEnum.ZH_TW.getLanguageCodeString());

        Response decryptResponse = AESUtil.getInstance().decryptResponseBody(findResponse.getBody());
        assertEquals("USD", decryptResponse.getCurrencyCategory().getCurrency());
        assertEquals("美金", decryptResponse.getCurrencyCategory().getCurrencyChineseName());

        log.info("==============================");
        log.info("單元測試1: ");
        log.info("find 【USD】 currency category: {}", decryptResponse.getCurrencyCategory());
        log.info("==============================");
    }


    @Test
    public void test_add() throws Exception {
        currencyCategoryController.deleteAll();
        CurrencyCategory currencyCategory = new CurrencyCategory("USD","美金", null);
        ResponseEntity<String> response = currencyCategoryController.add(AESUtil.getInstance().encrypt(currencyCategory));

        Response decryptResponse = AESUtil.getInstance().decryptResponseBody(response.getBody());
        assertEquals(ErrorCodeEnum.OK.getErrorCode(), decryptResponse.getStatusCode());
        assertEquals("USD", decryptResponse.getCurrencyCategoryList().get(0).getCurrency());
        assertEquals("美金", decryptResponse.getCurrencyCategoryList().get(0).getCurrencyChineseName());

        log.info("==============================");
        log.info("單元測試2-1: ");
        log.info("add 【USD】 currency category: {}", decryptResponse.getCurrencyCategoryList().get(0));
        log.info("==============================");
    }



    @Test
    public void test_addAll() throws Exception {
        currencyCategoryController.deleteAll();
        List<CurrencyCategory> currencyCategorys = Lists.list(
                new CurrencyCategory("USD","美金", null),
                new CurrencyCategory("EUR","歐元", null),
                new CurrencyCategory("GBP","英鎊", null));
        ResponseEntity<String> response = currencyCategoryController.addAll(AESUtil.getInstance().encrypt(currencyCategorys));

        Response decryptResponse = AESUtil.getInstance().decryptResponseBody(response.getBody());
        assertEquals(ErrorCodeEnum.OK.getErrorCode(), decryptResponse.getStatusCode());
        assertEquals("EUR", decryptResponse.getCurrencyCategoryList().get(1).getCurrency());
        assertEquals("歐元", decryptResponse.getCurrencyCategoryList().get(1).getCurrencyChineseName());

        log.info("==============================");
        log.info("單元測試2-2: ");
        log.info("add all currency category: {}", decryptResponse.getCurrencyCategoryList());
        log.info("==============================");
    }

    @Test
    public void test_update() throws Exception {
        currencyCategoryController.deleteAll();
        List<CurrencyCategory> currencyCategorys = Lists.list(
                new CurrencyCategory("USD","美金", null),
                new CurrencyCategory("EUR","歐元", null),
                new CurrencyCategory("GBP","英鎊", null));
        ResponseEntity<String> saveResponse = currencyCategoryController.addAll(AESUtil.getInstance().encrypt(currencyCategorys));
        ResponseEntity<String> response = currencyCategoryController.update("USD","美金_update");

        Response decryptResponse = AESUtil.getInstance().decryptResponseBody(response.getBody());
        assertEquals(ErrorCodeEnum.OK.getErrorCode(), decryptResponse.getStatusCode());
        assertEquals("USD", decryptResponse.getCurrencyCategory().getCurrency());
        assertEquals("美金_update", decryptResponse.getCurrencyCategory().getCurrencyChineseName());

        log.info("==============================");
        log.info("單元測試3: ");
        log.info("update currency category: {}", decryptResponse.getCurrencyCategory());
        log.info("==============================");
    }

    @Test
    public void test_delete() throws Exception {
        List<CurrencyCategory> currencyCategorys = Lists.list(
                new CurrencyCategory("USD","美金", new Date()),
                new CurrencyCategory("EUR","歐元", new Date()),
                new CurrencyCategory("GBP","英鎊", new Date()));
        ResponseEntity<String> saveResponse = currencyCategoryController.addAll(AESUtil.getInstance().encrypt(currencyCategorys));
        ResponseEntity<String> response = currencyCategoryController.delete("USD");

        Response decryptResponse = AESUtil.getInstance().decryptResponseBody(response.getBody());
        assertEquals(ErrorCodeEnum.OK.getErrorCode(), decryptResponse.getStatusCode());
        assertEquals("USD", decryptResponse.getCurrencyCategory().getCurrency());
        assertEquals("美金", decryptResponse.getCurrencyCategory().getCurrencyChineseName());

        log.info("==============================");
        log.info("單元測試4-1: ");
        log.info("delete currency category: {}", decryptResponse.getCurrencyCategory());
        log.info("==============================");
    }

    @Test
    public void test_deleteAll() throws Exception {
        ResponseEntity<String> response = currencyCategoryController.deleteAll();

        Response decryptResponse = AESUtil.getInstance().decryptResponseBody(response.getBody());
        assertEquals(ErrorCodeEnum.OK.getErrorCode(), decryptResponse.getStatusCode());

        log.info("==============================");
        log.info("單元測試4-2: ");
        log.info("delete all currency category");
        log.info("==============================");
    }
}
