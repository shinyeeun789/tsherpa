package com.chunjae.pro05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Pro05Application {

	public static void main(String[] args) {
		SpringApplication.run(Pro05Application.class, args);
	}

}
