package com.mm;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import tk.mybatis.spring.annotation.MapperScan;
@SpringBootApplication
@MapperScan(basePackages="com.mm.mapper")
@ComponentScan(basePackages = {"com.mm", "org.n3r.idworker"})
public class ComMMShortApplication extends WebMvcConfigurerAdapter {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ComMMShortApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ComMMShortApplication.class, args);
	}
	  
}
