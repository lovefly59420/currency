package com.joy.currency.util;

import com.joy.currency.config.CurrencyProperty;
import com.joy.currency.config.LocaleConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
@Slf4j
public class I18nUtil {

    @Autowired
    private CurrencyProperty currencyProperty;

    private String basename;

    @Autowired
    private LocaleConfig resolver;

    private static LocaleConfig customLocaleResolver;

    private static String path;

    @PostConstruct
    public void init() {
        this.basename = currencyProperty.getMessagesBasename();
        setBasename(basename);
        setCustomLocaleResolver(resolver);
    }

    /**
     * 獲取 國際化後內容訊息
     *
     * @param code 國際化 key
     * @return 國際化後內容訊息
     */
    public static String getMessage(String code) {
        Locale locale = customLocaleResolver.getLocal();
        return getMessage(code, null, code, locale);
    }

    /**
     * 獲取指定語言中的訊息，如果沒有預設繁忠
     *
     * @param code 國際化 key
     * @param lang 語言參數
     * @return 國際化後內容訊息
     */
    public static String getMessage(String code, String lang) {
        Locale locale;
        if (StringUtils.isEmpty(lang)) {
            locale = Locale.TAIWAN;
        } else {
            try {
                var split = lang.split("-");
                locale = new Locale(split[0], split[1]);
            } catch (Exception e) {
                locale = Locale.TAIWAN;
            }
        }
        return getMessage(code, null, code, locale);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        messageSource.setBasename(path);
        String content;
        try {
            content = messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            log.error("國際化參數取得失敗===>{},{}", e.getMessage(), e);
            content = defaultMessage;
        }
        return content;

    }

    public static void setBasename(String basename) {
        I18nUtil.path = basename;
    }

    public static void setCustomLocaleResolver(LocaleConfig resolver) {
        I18nUtil.customLocaleResolver = resolver;
    }
}
