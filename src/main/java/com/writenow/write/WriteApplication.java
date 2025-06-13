package com.writenow.write;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(WriteApplication.class, args);
	}

}
