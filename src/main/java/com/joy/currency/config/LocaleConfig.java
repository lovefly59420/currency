package com.joy.currency.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig implements LocaleResolver  {

    @Autowired
    private HttpServletRequest request;

    public Locale getLocal() {
        return resolveLocale(request);
    }

    /**
     * 從HttpServletRequest獲取Locale
     *
     * @param httpServletRequest    httpServletRequest
     * @return                      语言Local
     */
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        // 獲取請求中的語言參數
        String language = httpServletRequest.getParameter("lang");
        // 如果沒有就使用預設的，根據主機的語言環境生成一個Locale
        Locale locale = Locale.getDefault();
        // 請求攜帶了國際化參數
        if (!StringUtils.isEmpty(language)){
            //zh-TW
            String[] s = language.split("-");
            //國家、地區
            locale = new Locale(s[0], s[1]);
        }
        return locale;
    }

    /**
     * 用於實現Locale的切換。SessionLocaleResolver獲取Locale的方式是從session讀取，
     * 如果使用者想要切換語言，使用這裡的setLocale()
     *
     * @param request               HttpServletRequest
     * @param httpServletResponse   HttpServletResponse
     * @param locale                locale
     */
    @Override
    public void setLocale(@NonNull HttpServletRequest request,
                          @Nullable HttpServletResponse httpServletResponse,
                          @Nullable Locale locale) {

    }
}
