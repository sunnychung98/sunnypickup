package com.myapp.sunnypickup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
                .excludePathPatterns("/")
                .excludePathPatterns("/account/*")
                .excludePathPatterns("/test")
                .excludePathPatterns("/homeboard/*")
                .excludePathPatterns("/mypage/*");
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    @Bean
    MappingJackson2JsonView jsonView(){
		//jsonView
		return new MappingJackson2JsonView();
	}
    
}
