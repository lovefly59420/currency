package com.joy.currency.controller;

import com.joy.currency.dto.Response;
import com.joy.currency.dto.ServiceNameEnum;
import com.joy.currency.entity.CurrencyCategory;
import com.joy.currency.exception.CurrencyException;
import com.joy.currency.service.factory.AbstractFactory;
import com.joy.currency.service.interfac.ICurrencyCategoryService;
import com.joy.currency.util.AESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyCategoryController {

    @Autowired
    private AbstractFactory currencyCategoryServiceFactory;

    private AESUtil aesUtil = AESUtil.getInstance();

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody String encryptedCurrencyCategory) throws Exception {
        List<CurrencyCategory> saveData = aesUtil.decrypt(encryptedCurrencyCategory);
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.saveAll(saveData);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @PostMapping("/add/all")
    public ResponseEntity<String> addAll(@RequestBody String encryptedCurrencyCategory) throws Exception {
        List<CurrencyCategory> saveData = aesUtil.decrypt(encryptedCurrencyCategory);
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.saveAll(saveData);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestParam(value = "currency", required = true) String currency,
                                           @RequestParam(value = "currencyChineseName", required = true) String currencyChineseName) throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.update(currency, currencyChineseName);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @GetMapping("find/all")
    public ResponseEntity<String> findAll(@RequestParam(value = "lang", required = false) String lang) throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.findAll(lang);
        return ResponseEntity.status(200).body(aesUtil.encrypt(response));
    }

    @GetMapping("/find/{currency}")
    public ResponseEntity<String> findByCurrency(@PathVariable String currency, @RequestParam(value = "lang", required = false) String lang) throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.findByCurrency(currency, lang);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @DeleteMapping("/delete/{currency}")
    public ResponseEntity<String> delete(@PathVariable String currency) throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.delete(currency);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAll() throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.deleteAll();
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }
}
