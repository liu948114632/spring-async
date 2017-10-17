package com.liu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
//@ServletComponentScan
public class MySpringApplication {

	public static void main(String[] args) {

		SpringApplication.run(MySpringApplication.class, args);
	}
}
