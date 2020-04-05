package com.lgmn.adminapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
        //注意此处必须为前端地址，不能为*
        .allowedOrigins("*","http://localhost:8080", "http://localhost:8081", "http://192.168.1.7:8080","http://192.168.1.107:8080","http://192.168.192.10:8080","http://192.168.124.12:8080","http://192.168.1.118:8080","http://192.168.124.17:8080","http://192.168.2.238:8080")
        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
        .allowCredentials(true).maxAge(3600)
        .allowedHeaders("Origin","X-Requested-With","Content-Type","Accept","Authorization","Token");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}