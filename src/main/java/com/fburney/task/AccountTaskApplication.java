package com.fburney.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AccountTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountTaskApplication.class, args);
	}

}
