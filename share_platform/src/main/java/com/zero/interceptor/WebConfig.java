package com.zero.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
配置，重写addInterceptors方法，首先把LoginInterceptor类new出来，然后添加路径
addPathPatterns拦截，/admin/**（也就是admin之后不管什么都拦截），但是还需要排除一些
比如admin这里边还有登录的admin/login，都需要排除掉
 */
@Configuration//表明这是配置类
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login","/admin");
    }
}
