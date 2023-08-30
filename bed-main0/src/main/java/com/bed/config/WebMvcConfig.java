package com.bed.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    @Value("${uploadPath}")
//    String uploadPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/images/**")
                .addResourceLocations("file:///C:/shop/src/main/resources/static/images");
        registry.addResourceHandler("/asForm/**")
//                .addResourceLocations("file:///C:/shop/src/main/resources/static/asForm");
                .addResourceLocations("file:/C:/item/asForm/");
        registry.addResourceHandler("/item_images/**")
                .addResourceLocations("file:/C:/item/item_images/");



    }


}

