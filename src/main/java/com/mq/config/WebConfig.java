package com.mq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * mvc配置
 * @author Mr.Chang
 */
@EnableWebMvc
@Configuration
public class WebConfig extends DelegatingWebMvcConfiguration {

    /**
     * 静态资源拦截器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/favicon.ico").addResourceLocations("classpath:/static/", "/favicon.ico");
        super.addResourceHandlers(registry);
    }
}
