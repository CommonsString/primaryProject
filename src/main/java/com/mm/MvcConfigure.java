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
/*		//资源映射, /**访问所有的资源
		registry.addResourceHandler("/**")
		//资源路径, 映射目录file:本地目录
		.addResourceLocations("classpath:/META-INF/resources/")
		.addResourceLocations("file:C:/mm_video_workspace/");*/
		System.out.println("文件映射启动1");
		registry.addResourceHandler("/**")
		.addResourceLocations("classpath:/META-INF/resources/")
				.addResourceLocations("file:C:/mm_video_workspace/");
		System.out.println("文件映射启动2");
	}
}
