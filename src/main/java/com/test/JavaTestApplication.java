package com.test;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTestApplication.class, args);
		
		List<String> list=Arrays.asList(new String[] { "A", "B", "C", "D" });
		System.out.println(list);
		
	}

}
