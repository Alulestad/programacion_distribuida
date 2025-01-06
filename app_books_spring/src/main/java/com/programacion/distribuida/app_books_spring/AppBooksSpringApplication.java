package com.programacion.distribuida.app_books_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppBooksSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppBooksSpringApplication.class, args);
	}

}
