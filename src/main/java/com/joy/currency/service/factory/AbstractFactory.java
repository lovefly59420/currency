package com.joy.currency.service.factory;

import com.joy.currency.service.interfac.ICoindeskService;
import com.joy.currency.service.interfac.ICurrencyCategoryService;

public abstract class AbstractFactory {
    public abstract ICoindeskService getICoindeskService(String serviceClassName);
    public abstract ICurrencyCategoryService getICurrencyCategoryService(String serviceClassName);
}
