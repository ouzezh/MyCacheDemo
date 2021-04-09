package com.ozz.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MySpringApp {

	public static void main(String[] args) {
		SpringApplication.run(MySpringApp.class, args);
	}

}
