package com.joy.currency.controller;

import com.joy.currency.dto.Response;
import com.joy.currency.dto.ServiceNameEnum;
import com.joy.currency.service.factory.AbstractFactory;
import com.joy.currency.service.interfac.ICoindeskService;
import com.joy.currency.util.AESUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coindesk")
@Tag(name = "coindesk 相關服務", description="查詢、轉換資料")
public class CoindeskController {
    @Autowired
    private AbstractFactory coindeskServiceFactory;

    private AESUtil aesUtil = AESUtil.getInstance();

    @Operation(summary = "取得 coindesk API 資料")
    @GetMapping("/get/data")
    public ResponseEntity<String> getData() throws Exception {
        ICoindeskService service = coindeskServiceFactory.getICoindeskService(ServiceNameEnum.COINDESK_SERVICE.getName());
        Response response = service.getCoindesk();
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }

    @Operation(summary = "將 coindesk API 資料轉換成特定格式")
    @GetMapping("/transform/data")
    public ResponseEntity<String> transformData() throws Exception {
        ICoindeskService service = coindeskServiceFactory.getICoindeskService(ServiceNameEnum.COINDESK_SERVICE.getName());
        Response response = service.transformCoindesk();
        return ResponseEntity.status(response.getStatusCode()).body(aesUtil.encrypt(response));
    }
}
