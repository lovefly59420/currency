package com.joy.currency.utilTest;

import com.joy.currency.config.LocaleConfig;
import com.joy.currency.dto.LanguageCodeEnum;
import com.joy.currency.util.I18nUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class I18nUtilTest {

    @Mock
    private LocaleConfig mockLocaleConfig;

    private ResourceBundleMessageSource messageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化測試環境
        I18nUtil.setCustomLocaleResolver(mockLocaleConfig);

        messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("messages");
        I18nUtil.setBasename("messages");
    }

    @Test
    void test_getMessage_withDefaultLocale() {
        // 模擬 LocaleConfig 返回預設語系
        when(mockLocaleConfig.getLocal()).thenReturn(Locale.TAIWAN);

        String key = "USD.message";
        String expectedMessage = "美金";

        messageSource.setBasename("messages");
        String actualMessage = I18nUtil.getMessage(key);

        assertEquals(expectedMessage, actualMessage, "應返回默認語系的國際化訊息");
    }

    @Test
    void test_getMessage_withSpecificLanguage() {
        // 測試指定語系 (en-US)
        String key = "USD.message";
        String expectedMessage = "USD";

        String actualMessage = I18nUtil.getMessage(key, LanguageCodeEnum.EN_US.getLanguageCodeString());

        assertEquals(expectedMessage, actualMessage, "應返回指定語系的國際化訊息");
    }

    @Test
    void test_getMessage_KeyNotFound() {
        // 測試鍵不存在時返回默認值
        String key = "non.existent.key";
        String defaultMessage = key;

        String actualMessage = I18nUtil.getMessage(key);

        assertEquals(defaultMessage, actualMessage, "應返回默認訊息");
    }
}
