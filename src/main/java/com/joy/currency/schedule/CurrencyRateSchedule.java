package com.joy.currency.schedule;

import com.joy.currency.dto.*;
import com.joy.currency.entity.CurrencyCategory;
import com.joy.currency.service.factory.AbstractFactory;
import com.joy.currency.service.interfac.ICoindeskService;
import com.joy.currency.service.interfac.ICurrencyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class CurrencyRateSchedule {

    @Autowired
    private AbstractFactory currencyCategoryServiceFactory;

    @Autowired
    private AbstractFactory coindeskServiceFactory;

    /**
     * fixedDelay = 10*60*1000  (10 分鐘 1 次)
     * cron = "0 0 0 * * ?"     (晚上 12 點)
     */
    @Scheduled(fixedDelay = 3*60*1000)
    public void syncCurrencyRate(){
        ICoindeskService coinService = coindeskServiceFactory.getICoindeskService(ServiceNameEnum.COINDESK_SERVICE.getName());
        Currentprice currentprice = coinService.getCurrentpriceFromApi();
        if(CollectionUtils.isEmpty(currentprice.getBpi())){
            return;
        }
        Map<String,Float> currencyRateMap = this.getCurrencyRateMap(currentprice.getBpi());
        List<CurrencyCategory> updateCurrencyCategory = getUpdateCurrencyCategory(currencyRateMap);
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        service.saveAll(updateCurrencyCategory);
    }

    public Map<String,Float> getCurrencyRateMap(Map<String, BpiDetail> bpi){
        Map<String,Float> result = new HashMap<>();
        for(String key : bpi.keySet()){
            BpiDetail bpiDetail = bpi.get(key);
            if(bpiDetail != null && !bpiDetail.getRate_float().isNaN()){
                result.put(key,bpiDetail.getRate_float());
            }
        }
        return result;
    }

    public List<CurrencyCategory> getUpdateCurrencyCategory(Map<String,Float> currencyRateMap){
        ICurrencyCategoryService service = currencyCategoryServiceFactory.getICurrencyCategoryService(ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName());
        List<CurrencyCategory> result = new ArrayList<>();
        for(String key : currencyRateMap.keySet()){
            Response response = service.findByCurrency(key, null);
            if(response.getStatusCode() == ErrorCodeEnum.OK.getErrorCode()){
                CurrencyCategory oldCurrencyCategory = response.getCurrencyCategory();
                oldCurrencyCategory.setRate_float(currencyRateMap.get(key));
                oldCurrencyCategory.setUpdateTime(new Date());
                result.add(oldCurrencyCategory);
            }
        }
        return result;
    }
}
