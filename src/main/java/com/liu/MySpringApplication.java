package com.liu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@EnableCaching
//@ServletComponentScan
public class MySpringApplication {
	public static void changeStr(int s){
		s=++s;
		System.out.println(s);
	}

	public static void main(String[] args) {
		int s = 1;
		changeStr(s);
		System.out.println(s);

		SpringApplication.run(MySpringApplication.class, args);
	}
}
