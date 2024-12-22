package com.joy.currency.service.factory;

import com.joy.currency.dto.ServiceNameEnum;
import com.joy.currency.service.interfac.ICoindeskService;
import com.joy.currency.service.interfac.ICurrencyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoindeskServiceFactory extends AbstractFactory{

    @Autowired
    private ICoindeskService coindeskService;

    @Override
    public ICoindeskService getICoindeskService(String serviceClassName) {
        if (ServiceNameEnum.COINDESK_SERVICE.getName().equalsIgnoreCase(serviceClassName)) {
            return coindeskService;
        }

        throw new IllegalArgumentException("No such service type: " + serviceClassName);
    }

    @Override
    public ICurrencyCategoryService getICurrencyCategoryService(String serviceClassName) {
        return null;
    }
}
