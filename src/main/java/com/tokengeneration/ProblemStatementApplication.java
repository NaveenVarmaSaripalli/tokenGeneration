package com.tokengeneration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackages = "com.tokengeneration")
@EnableAutoConfiguration
public class ProblemStatementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProblemStatementApplication.class, args);
	}
}