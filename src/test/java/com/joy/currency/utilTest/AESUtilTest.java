package com.joy.currency.utilTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joy.currency.dto.Response;
import com.joy.currency.entity.CurrencyCategory;
import com.joy.currency.util.AESUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AESUtilTest {
    private AESUtil aesUtil;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // 使用單例
        aesUtil = AESUtil.getInstance();
        // 用於驗證 JSON
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_encryptAndDecrypt_response() throws Exception {
        // 測試數據
        Response response = new Response();
        response.setStatusCode(200);
        response.setMessage("Success");

        // 加密
        String encryptedText = aesUtil.encrypt(response);

        // 解密
        Response decryptedResponse = aesUtil.decryptResponseBody(encryptedText);

        // 驗證結果
        assertEquals(response.getStatusCode(), decryptedResponse.getStatusCode());
        assertEquals(response.getMessage(), decryptedResponse.getMessage());
    }

    @Test
    void test_encryptAndDecrypt_singleCurrencyCategory() throws Exception {
        // 測試數據
        CurrencyCategory category = new CurrencyCategory();
        category.setSid(1L);
        category.setCurrency("USD");

        // 加密
        String encryptedText = aesUtil.encrypt(category);

        // 解密
        List<CurrencyCategory> decryptedCategories = aesUtil.decrypt(encryptedText);

        // 驗證結果
        assertEquals(1, decryptedCategories.size());
        assertEquals(category.getSid(), decryptedCategories.get(0).getSid());
        assertEquals(category.getCurrency(), decryptedCategories.get(0).getCurrency());
    }

    @Test
    void test_encryptAndDecrypt_listOfCurrencyCategories() throws Exception {
        // 測試數據
        CurrencyCategory category1 = new CurrencyCategory();
        category1.setSid(1L);
        category1.setCurrency("USD");

        CurrencyCategory category2 = new CurrencyCategory();
        category2.setSid(2L);
        category2.setCurrency("EUR");

        List<CurrencyCategory> categories = List.of(category1, category2);

        // 加密
        String encryptedText = aesUtil.encrypt(categories);

        // 解密
        List<CurrencyCategory> decryptedCategories = aesUtil.decrypt(encryptedText);

        // 驗證結果
        assertEquals(categories.size(), decryptedCategories.size());
        assertEquals(categories.get(0).getCurrency(), decryptedCategories.get(0).getCurrency());
        assertEquals(categories.get(1).getCurrency(), decryptedCategories.get(1).getCurrency());
    }

    @Test
    void test_decrypt_invalidData() {
        // 測試無效加密文本
        String invalidData = "invalid-encrypted-text";

        Exception exception = assertThrows(Exception.class, () -> {
            aesUtil.decrypt(invalidData);
        });
    }
}
