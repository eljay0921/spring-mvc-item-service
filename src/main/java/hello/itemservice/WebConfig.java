package hello.itemservice;

import hello.itemservice.web.filter.LogFilter;
import hello.itemservice.web.filter.LoginCheckFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

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
}
