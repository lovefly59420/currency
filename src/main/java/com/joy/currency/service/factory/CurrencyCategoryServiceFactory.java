package com.joy.currency.service.factory;

import com.joy.currency.dto.ServiceNameEnum;
import com.joy.currency.service.interfac.ICoindeskService;
import com.joy.currency.service.interfac.ICurrencyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyCategoryServiceFactory extends AbstractFactory {

    @Autowired
    private ICurrencyCategoryService currencyCategoryService;

    @Override
    public ICoindeskService getICoindeskService(String serviceClassName) {
        return null;
    }

    @Override
    public ICurrencyCategoryService getICurrencyCategoryService(String serviceClassName) {
        if (ServiceNameEnum.CURRENCY_CATEGORY_SERVICE.getName().equalsIgnoreCase(serviceClassName)) {
            return currencyCategoryService;
        }
        // 這裡可擴展其他條件返回不同的實現
        throw new IllegalArgumentException("No such service type: " + serviceClassName);
    }
}
