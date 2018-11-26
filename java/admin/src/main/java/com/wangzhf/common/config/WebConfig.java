package com.wangzhf.common.config;

import com.wangzhf.common.config.converter.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class WebConfig {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void initEditableValidation() {
        ConfigurableWebBindingInitializer configurableWebBindingInitializer =
            (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if (configurableWebBindingInitializer.getConversionService() != null) {
            GenericConversionService service = (GenericConversionService) configurableWebBindingInitializer.getConversionService();
            // 添加StringToDateConverter，用于处理请求中字符串转日期
            service.addConverter(new StringToDateConverter());
        }
    }

}
