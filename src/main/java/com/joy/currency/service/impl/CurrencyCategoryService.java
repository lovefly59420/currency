package com.joy.currency.service.impl;

import com.joy.currency.dto.*;
import com.joy.currency.entity.CurrencyCategory;
import com.joy.currency.exception.CurrencyException;
import com.joy.currency.repository.CurrencyCategoryRepository;
import com.joy.currency.service.interfac.ICurrencyCategoryService;
import com.joy.currency.util.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyCategoryService implements ICurrencyCategoryService {

    @Autowired
    private CurrencyCategoryRepository currencyCategoryRepository;

    @Override
    public Response findAll(String lang) throws Exception {
        Response response = new Response();
        try{
            List<CurrencyCategory> result = currencyCategoryRepository.findAll().stream().sorted(Comparator.comparing(CurrencyCategory::getCurrency)).toList();
            if(lang != null && !LanguageCodeEnum.ZH_TW.getLanguageCodeString().equals(lang)){
                for(CurrencyCategory currencyCategory : result){
                    String currencyName = I18nUtil.getMessage(currencyCategory.getCurrency()+"." + CurrencyCodeEnum.MESSAGE.getCode(), lang);
                    currencyCategory.setCurrencyChineseName(currencyName);
                }
            }
            response.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
            response.setMessage(MessageEnum.FIND_ALL_SUCCESS.getMessage());
            response.setCurrencyCategoryList(result);
        }catch (Exception e){
            response.setStatusCode(ErrorCodeEnum.EXCEPTION.getErrorCode());
            response.setMessage(MessageEnum.FIND_ALL_ERROR.getMessage());
        }
        return response;
    }

    @Override
    public Response findByCurrency(String currency, String lang) {
        Response response = new Response();
        try{
            CurrencyCategory result = currencyCategoryRepository.findByCurrency(currency).orElseThrow(() -> new CurrencyException(MessageEnum.CURRENCY_CATEGORY_NOT_FOUND.getMessage()));
            if(lang != null && !LanguageCodeEnum.ZH_TW.getLanguageCodeString().equals(lang)){
                String currencyName = I18nUtil.getMessage(result.getCurrency()+"." + CurrencyCodeEnum.MESSAGE.getCode(), lang);
                result.setCurrencyChineseName(currencyName);
            }
            response.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
            response.setCurrencyCategory(result);
            response.setMessage(MessageEnum.FIND_BY_SUCCESS.getMessage());
        } catch (CurrencyException e){
            response.setStatusCode(ErrorCodeEnum.CURRENCY_EXCEPTION.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(ErrorCodeEnum.EXCEPTION.getErrorCode());
            response.setMessage(MessageEnum.FIND_BY_ERROR.getMessage() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response saveAll(List<CurrencyCategory> currencyCategorys){
        Response response = new Response();
        try{
            currencyCategorys.forEach(currencyCategory->currencyCategory.setCreateTime(new Date()));
            List<CurrencyCategory> result = currencyCategoryRepository.saveAll(currencyCategorys);

            response.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
            response.setCurrencyCategoryList(result);
            response.setMessage(MessageEnum.SAVE_ALL_SUCCESS.getMessage());
        } catch (Exception e){
            response.setStatusCode(ErrorCodeEnum.EXCEPTION.getErrorCode());
            response.setMessage(MessageEnum.SAVE_ALL_ERROR.getMessage() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response update(String currency, String currencyChineseName) {
        Response response = new Response();
        try{
            CurrencyCategory oldData = currencyCategoryRepository.findByCurrency(currency).orElseThrow(() -> new CurrencyException(MessageEnum.CURRENCY_CATEGORY_NOT_FOUND.getMessage()));
            if(currencyChineseName != null && !currencyChineseName.isEmpty()){
                oldData.setCurrencyChineseName(currencyChineseName);
            }
            oldData.setUpdateTime(new Date());
            CurrencyCategory result = currencyCategoryRepository.save(oldData);

            response.setCurrencyCategory(result);
            response.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
            response.setMessage(MessageEnum.UPDATE_SUCCESS.getMessage());
        } catch (CurrencyException e){
            response.setStatusCode(ErrorCodeEnum.CURRENCY_EXCEPTION.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(ErrorCodeEnum.EXCEPTION.getErrorCode());
            response.setMessage(MessageEnum.UPDATE_ERROR.getMessage() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response delete(String currency) {
        Response response = new Response();
        try{
            CurrencyCategory result = currencyCategoryRepository.findByCurrency(currency).orElseThrow(() -> new CurrencyException(MessageEnum.CURRENCY_CATEGORY_NOT_FOUND.getMessage()));
            currencyCategoryRepository.delete(result);

            response.setCurrencyCategory(result);
            response.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
            response.setMessage(MessageEnum.DELETE_SUCCESS.getMessage());
        }catch (CurrencyException e){
            response.setStatusCode(ErrorCodeEnum.CURRENCY_EXCEPTION.getErrorCode());
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(ErrorCodeEnum.EXCEPTION.getErrorCode());
            response.setMessage(MessageEnum.DELETE_ERROR.getMessage() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteAll() {
        Response response = new Response();
        try{
            currencyCategoryRepository.deleteAll();

            response.setStatusCode(ErrorCodeEnum.OK.getErrorCode());
            response.setMessage(MessageEnum.DELETE_SUCCESS.getMessage());
        }catch (Exception e){
            response.setStatusCode(ErrorCodeEnum.EXCEPTION.getErrorCode());
            response.setMessage(MessageEnum.DELETE_ERROR.getMessage() + e.getMessage());
        }
        return response;
    }
}
