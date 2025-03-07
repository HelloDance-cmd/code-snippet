package com.brick.codesnippetbackend.config;

import com.brick.codesnippetbackend.interceptor.ResponseWithTokenInterceptor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@NoArgsConstructor
@Slf4j
public class RegisterInterceptorConfig implements WebMvcConfigurer
{
    private ResponseWithTokenInterceptor responseWithTokenInterceptor;

    @Autowired
    RegisterInterceptorConfig(ResponseWithTokenInterceptor responseWithTokenInterceptor)
    {
        this.responseWithTokenInterceptor = responseWithTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(responseWithTokenInterceptor)
                .addPathPatterns("/user/**", "/snippet/**", "/dashboard/**")
                .excludePathPatterns("/user/login", "/user/register", "/user/is-expired");

        log.info("RegisterInterceptor addInterceptors");
    }
}
