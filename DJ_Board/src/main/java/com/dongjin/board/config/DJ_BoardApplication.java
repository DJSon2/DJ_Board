package com.dongjin.board.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/*
 * Starter 등록한 것들
 * Spring Boot DevTools
 * JDBC API
 * Spring Data JPA
 * Oracle Driver
 * Thymeleaf
 * Spring Web
 */
@SpringBootApplication
@ComponentScan("com.dongjin.board")
public class DJ_BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DJ_BoardApplication.class, args);
	}
	
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}
