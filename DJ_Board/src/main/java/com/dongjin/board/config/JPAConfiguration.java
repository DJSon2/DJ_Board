package com.dongjin.board.config;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.dongjin.board"})
@EnableJpaRepositories(basePackages = "com.dongjin.board")
public class JPAConfiguration {

}
