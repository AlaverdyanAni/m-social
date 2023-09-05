package com.github.alaverdyanani.msocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MSocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(MSocialApplication.class, args);
	}

}
