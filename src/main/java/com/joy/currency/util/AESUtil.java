package com.joy.currency.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joy.currency.dto.Response;
import com.joy.currency.entity.CurrencyCategory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

public class AESUtil {
    // AES-128 :16字符
    private static final String AES_KEY = "Sdk6CA947djUGP7N";

    private static final AESUtil instance = new AESUtil();

    // 私有建構子，防止外部實例化
    private AESUtil() {}

    // 提供全域唯一訪問點
    public static AESUtil getInstance() {
        return instance;
    }

    public String encrypt(Response responseBody) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseBody);
        SecretKey key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(jsonResponse.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String encrypt(CurrencyCategory currencyCategory) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(currencyCategory);
        SecretKey key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(jsonResponse.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String encrypt(List<CurrencyCategory> currencyCategorys) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(currencyCategorys);
        SecretKey key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(jsonResponse.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    public List<CurrencyCategory> decrypt(String encryptedText) throws Exception {
        SecretKey key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        String decryptedJson = new String(decryptedBytes);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(decryptedJson);
        if(jsonNode.isArray()){
            List<CurrencyCategory> result = objectMapper.readValue(decryptedJson, new TypeReference<>() {
            });
            return result;
        }
        CurrencyCategory result = objectMapper.readValue(decryptedJson, CurrencyCategory.class);
        return List.of(result);
    }

    public Response decryptResponseBody(String encryptedText) throws Exception {
        SecretKey key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        String decryptedJson = new String(decryptedBytes);
        ObjectMapper objectMapper = new ObjectMapper();
        Response result = objectMapper.readValue(decryptedJson, Response.class);
        return result;
    }
}
