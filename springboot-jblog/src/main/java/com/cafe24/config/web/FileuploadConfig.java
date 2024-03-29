package com.cafe24.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:com/cafe24/config/web/properties/multipart.properties")
public class FileuploadConfig extends WebMvcConfigurerAdapter {

	private Environment env;
	
	@Bean
	public CommonsMultipartResolver commonMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(env.getProperty("maxUploadSize",Long.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("maxInMemorySize",Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("defaultEncoding"));
		
		return multipartResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets-upload/images/**").addResourceLocations("file:/jblog-uploads/");
	}
	
}
