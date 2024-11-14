package UniP_server_chat.Unip_party_chat.global.config;

import UniP_server_chat.Unip_party_chat.global.filter.JWTCheckFilter;
import UniP_server_chat.Unip_party_chat.global.filter.JWTResponseFilter;
import UniP_server_chat.Unip_party_chat.global.filter.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<JWTResponseFilter> jwtResponseFilter(JWTResponseFilter jwtResponseFilter) {
        FilterRegistrationBean<JWTResponseFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtResponseFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<JWTCheckFilter> jwtCheckFilter(JWTCheckFilter jwtCheckFilter) {
        FilterRegistrationBean<JWTCheckFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtCheckFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
