package com.programacion.distribuida.app_books_spring.rest;

import com.programacion.distribuida.app_books_spring.dto.AuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/books_client")
@CrossOrigin
public class AuthorClientController {

    private final RestTemplate restTemplate;

    public AuthorClientController(@LoadBalanced RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping(path="/authors/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorDto getAuthorsFromLoadBalancedInstance(@PathVariable Integer id) {
        String url = "http://app-authors-spring/authors/" + id; // Nombre del servicio registrado en Consul

        return restTemplate.getForObject(url, AuthorDto.class);
    }


}
