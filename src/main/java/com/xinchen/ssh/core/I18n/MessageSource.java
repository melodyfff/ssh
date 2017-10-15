package com.xinchen.ssh.core.I18n;

import com.xinchen.ssh.demo.dao.I18nResourceDao;
import com.xinchen.ssh.demo.entity.I18nResource;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;

@Component("messageSource")
public class MessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private I18nResourceDao i18nResourceDao;

    public I18nResourceDao getI18nResourceDao() {
        return i18nResourceDao;
    }

    public void setI18nResourceDao(I18nResourceDao i18nResourceDao) {
        this.i18nResourceDao = i18nResourceDao;
    }

    /**
     * Map切分字符
     */
    protected final String MAP_SPLIT_CODE = "|";
    private final Map<String, String> properties = new HashMap<String, String>();

    public MessageSource() {
//        reload();
    }

    public void reload() {
        properties.clear();
        properties.putAll(loadTexts());
    }

    /**
     *
     * 描述：TODO
     * 查询数据 虚拟数据，可以从数据库读取信息，此处省略
     * @return
     */
    private List<I18nResource> getResource(){
        return i18nResourceDao.findAll();
    }

    /**
     *
     * 描述：TODO
     * 加载数据
     * @return
     */
    private Map<String, String> loadTexts() {
        Map<String, String> mapResource = new HashMap<>();
        List<I18nResource> resources = this.getResource();
        for (I18nResource item : resources) {
            String code = item.getName() + MAP_SPLIT_CODE + item.getLanguage();
            mapResource.put(code, item.getContent());
        }
        return mapResource;
    }

    /**
     *
     * 描述：TODO
     * @param code
     * @param locale 本地化语言
     * @return
     */
    private String getContent(String code, Locale locale) {
        String localeCode = locale.getLanguage();
        String key = code + MAP_SPLIT_CODE + localeCode;
        String localeText = properties.get(key);
        String resourceText = code;

        if(localeText != null) {
            resourceText = localeText;
        }
        else {
            localeCode = Locale.ENGLISH.getLanguage();
            key = code + MAP_SPLIT_CODE + localeCode;
            localeText = properties.get(key);
            if(localeText != null) {
                resourceText = localeText;
            }
            else {
                try {
                    if(getParentMessageSource() != null) {
                        resourceText = getParentMessageSource().getMessage(code, null, locale);
                    }
                } catch (Exception e) {
                    logger.error("Cannot find message with code: " + code);
                }
            }
        }
        return resourceText;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader != null ? resourceLoader : new DefaultResourceLoader());
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getContent(code, locale);
        return createMessageFormat(msg, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getContent(code, locale);
    }



}
