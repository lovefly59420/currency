package com.joy.currency.controllerTest;

import com.joy.currency.controller.CoindeskController;
import com.joy.currency.controller.CurrencyCategoryController;
import com.joy.currency.dto.ErrorCodeEnum;
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
public class CoindeskControllerTest {

    @Autowired
    private CoindeskController coindeskController;

    @Autowired
    private CurrencyCategoryController currencyCategoryController;

    @Test
    public void test_getData() throws Exception {
        ResponseEntity<String> response = coindeskController.getData();
        Response decryptResponse = AESUtil.getInstance().decryptResponseBody(response.getBody());
        assertEquals(ErrorCodeEnum.OK.getErrorCode(), decryptResponse.getStatusCode());

        log.info("==============================");
        log.info("單元測試5: ");
        log.info("coindesk API: {}", decryptResponse.getCurrentprice());
        log.info("==============================");
    }

    @Test
    public void test_transformData() throws Exception {
        currencyCategoryController.deleteAll();
        List<CurrencyCategory> currencyCategorys = Lists.list(
                new CurrencyCategory("USD","美金", new Date()),
                new CurrencyCategory("EUR","歐元", new Date()),
                new CurrencyCategory("GBP","英鎊", new Date()));
        ResponseEntity<String> saveResponse = currencyCategoryController.addAll(AESUtil.getInstance().encrypt(currencyCategorys));

        ResponseEntity<String> response = coindeskController.transformData();
        Response decryptResponse = AESUtil.getInstance().decryptResponseBody(response.getBody());
        assertEquals(ErrorCodeEnum.OK.getErrorCode(), decryptResponse.getStatusCode());

        log.info("==============================");
        log.info("單元測試6:");
        log.info("更新時間: {}", decryptResponse.getUpdateTimeString());
        log.info("幣別相關資訊: {}", decryptResponse.getCurrencyInformation());
        log.info("==============================");
    }
}
