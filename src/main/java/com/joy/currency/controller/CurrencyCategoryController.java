package com.joy.currency.controller;

import com.joy.currency.dto.Response;
import com.joy.currency.dto.ServiceNameEnum;
import com.joy.currency.entity.CurrencyCategory;
import com.joy.currency.service.factory.AbstractFactory;
import com.joy.currency.service.interfac.ICurrencyCategoryService;
import com.joy.currency.util.AESUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
@Tag(name = "幣別管理", description="新增、刪除、查詢、改正")
@OpenAPIDefinition(info = @Info(title = "幣別管理文件", version = "0.0.1"))
public class CurrencyCategoryController {

    @Autowired
    private AbstractFactory currencyCategoryServiceFactory;

    private AESUtil aesUtil = AESUtil.getInstance();

    @Operation(summary = "新增一筆幣別資料", description = "只能新增一筆")
    @ApiResponse(responseCode = "200", description = "成功新增幣別資料")
    @ApiResponse(responseCode = "500", description = "失敗")
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody String encryptedCurrencyCategory) throws Exception {
        List<CurrencyCategory> saveData = aesUtil.decrypt(encryptedCurrencyCategory);
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.saveAll(saveData);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @Operation(summary = "新增多筆幣別資料", description = "可新增多筆")
    @PostMapping("/add/all")
    public ResponseEntity<String> addAll(@RequestBody String encryptedCurrencyCategory) throws Exception {
        List<CurrencyCategory> saveData = aesUtil.decrypt(encryptedCurrencyCategory);
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.saveAll(saveData);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @Operation(summary = "更新幣別資料", description = "使用幣別代碼更新幣別中文名稱")
    @PutMapping("update")
    public ResponseEntity<String> update(@RequestParam(value = "currency", required = true) String currency,
                                           @RequestParam(value = "currencyChineseName", required = true) String currencyChineseName) throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.update(currency, currencyChineseName);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @Operation(summary = "取得所有幣別資料")
    @GetMapping("find/all")
    public ResponseEntity<String> findAll(@RequestParam(value = "lang", required = false) String lang) throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.findAll(lang);
        return ResponseEntity.status(200).body(aesUtil.encrypt(response));
    }

    @Operation(summary = "取得特定幣別資料", description = "使用幣別代碼取得幣別資料")
    @GetMapping("/find/{currency}")
    public ResponseEntity<String> findByCurrency(@PathVariable String currency, @RequestParam(value = "lang", required = false) String lang) throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.findByCurrency(currency, lang);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @Operation(summary = "刪除特定幣別資料", description = "使用幣別代碼刪除幣別資料")
    @DeleteMapping("/delete/{currency}")
    public ResponseEntity<String> delete(@PathVariable String currency) throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.delete(currency);
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @Operation(summary = "刪除所有幣別資料")
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAll() throws Exception {
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        Response response = service.deleteAll();
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }
}
