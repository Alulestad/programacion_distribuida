package com.programacion.distribuida.app_authors_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppAuthorsSpringApplication{

    public static void main(String[] args) {
        SpringApplication.run(AppAuthorsSpringApplication.class, args);

    }

}
