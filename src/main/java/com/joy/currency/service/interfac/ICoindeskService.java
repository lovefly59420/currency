package com.joy.currency.service.interfac;

import com.joy.currency.dto.Currentprice;
import com.joy.currency.dto.Response;

public interface ICoindeskService {

    Response getCoindesk();

    Response transformCoindesk();

    Currentprice getCurrentpriceFromApi();
}
