package com.joy.currency.service.interfac;

import com.joy.currency.dto.Response;
import com.joy.currency.entity.CurrencyCategory;

import java.util.List;

public interface ICurrencyCategoryService {

    Response findAll(String lang) throws Exception;

    Response findByCurrency(String currency, String lang);

    Response saveAll(List<CurrencyCategory> currencyCategorys);

    Response update(String currency, String currencyChineseName);

    Response delete(String currency);

    Response deleteAll();
}
