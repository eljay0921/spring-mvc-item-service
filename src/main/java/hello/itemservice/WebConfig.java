package hello.itemservice;

import hello.itemservice.web.filter.LogFilter;
import hello.itemservice.web.filter.LoginCheckFilter;
import hello.itemservice.web.interceptor.LogInterceptor;
import hello.itemservice.web.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Loggger
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        // Login Checker
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/items**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
    }

/*
    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);             // Filter 순서
        filterRegistrationBean.addUrlPatterns("/*");    // 모든 url에 적용
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);             // Filter 순서
        filterRegistrationBean.addUrlPatterns("/*");    // 모든 url에 적용 (whitelist는 각 filter 클래스에서 정의하는 게 변경에 용이하다)
        return filterRegistrationBean;
    }
*/
}
