package com.curso.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		   registry.addResourceHandler("/webjars/**", "/resources/**", "/static/**", "/img/**", "/css/**", "/js/**",
						"classpath:/static/", "classpath:/resources/")
				.addResourceLocations("/webjars/", "/resources/",
								"classpath:/static/**", "classpath:/static/img/**", "classpath:/static/",
								"classpath:/resources/", "classpath:/static/css/", "classpath:/static/js/", "/resources/**",
								"/WEB-INF/classes/static/**");
		}
}
