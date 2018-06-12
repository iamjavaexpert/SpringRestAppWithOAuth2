package com.spring.rest.sample.SpringSecRestDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({ "com.spring.rest.sample.model" })
@ComponentScan(basePackages = { "com.spring.rest.sample" })
@EnableJpaRepositories(basePackages = { "com.spring.rest.sample.repository" })
public class SpringSecRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecRestDemoApplication.class, args);
	}
}
