package com.myapp.sunnypickup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * servelt 관련 설정
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ProcessInterceptor())
                .addPathPatterns("/*")
                .addPathPatterns("/mypage/*")
                .excludePathPatterns("/")
                .excludePathPatterns("/account/*")
                .excludePathPatterns("/qna/*")
                .excludePathPatterns("/shop/*");
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/uploadImg/**").addResourceLocations("file:///E:/SpringBootProject/files/")
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
    @Bean
    MappingJackson2JsonView jsonView(){
        //jsonView
        return new MappingJackson2JsonView();
    }

}