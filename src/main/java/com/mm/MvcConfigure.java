package com.mm;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * tomcat虚拟路径 
 * WebMvcConfigurerAdapter配置类其实是Spring内部的一种配置方。
 * 采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制
 * 
 * classpath: 相对路径的映射
 * file：绝对路径的映射
 * 相当于是xml
 */
@Configuration
public class MvcConfigure extends WebMvcConfigurerAdapter{
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/uploads/**")
		registry.addResourceHandler("/**/")
				.addResourceLocations("classpath:/META-INF/resources/")
				.addResourceLocations("file:C:/mm_video_workspace/");
	}
}
