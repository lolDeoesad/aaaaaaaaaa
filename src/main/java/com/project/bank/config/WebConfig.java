package com.project.bank.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000")
				.allowedOrigins("https://aaaaaa-40e58.web.app")
				.allowedMethods("GET", "POST", "DELETE", "PUT");
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}