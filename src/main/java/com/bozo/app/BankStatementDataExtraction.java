package com.bozo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankStatementDataExtraction {

	public static void main(String[] args) {
		SpringApplication.run(BankStatementDataExtraction.class, args);
	}

}
