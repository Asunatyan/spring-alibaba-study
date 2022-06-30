package com.tuling.mall.sentineldemo.config;


import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.tuling.mall.sentineldemo.sentinel.MyRequestOriginParser;
import com.tuling.mall.sentineldemo.sentinel.MyUrlBlockHandler;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://github.com/alibaba/Sentinel/issues/1213
 */
//@Configuration
public class SentinelConfig {

    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        // 入口资源关闭聚合  解决流控链路不生效的问题
        registration.addInitParameter(CommonFilter.WEB_CONTEXT_UNIFY, "false");
        registration.setName("sentinelFilter");
        registration.setOrder(1);

        //CommonFilter的BlockException自定义处理逻辑
        WebCallbackManager.setUrlBlockHandler(new MyUrlBlockHandler());

        //解决授权规则不生效的问题
        //com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser
        //WebCallbackManager.setRequestOriginParser(new MyRequestOriginParser());

        return registration;
    }


}