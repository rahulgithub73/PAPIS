package com.dan.papis.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dan.papis.filter.LoginFilter;

@Configuration
//@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
public class AppConfig implements WebMvcConfigurer {

	@Bean
	public FilterRegistrationBean<LoginFilter> corsFilterRegistration() {
		FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>(loginFilter());
		List<String> urlPatterns = new ArrayList<>();
		
		
		urlPatterns.add("/index/*");
		urlPatterns.add("/deviceConfiguration/*");
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		return filterRegistrationBean;
	}

	@Bean
	public LoginFilter loginFilter() {
		return new LoginFilter();
	}
	
	

}