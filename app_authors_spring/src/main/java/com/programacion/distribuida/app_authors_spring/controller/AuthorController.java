package com.programacion.distribuida.app_authors_spring.controller;

import com.programacion.distribuida.app_authors_spring.dto.AuthorDto;
import com.programacion.distribuida.app_authors_spring.repository.modelo.Author;
import com.programacion.distribuida.app_authors_spring.service.AuthorService;
import com.programacion.distribuida.app_authors_spring.service.AuthorServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@CrossOrigin
public class AuthorController {

    private final AuthorService authorService;

    //Tranformacion de objetos
    private final ModelMapper modelMapper = new ModelMapper();

    private AuthorDto toAuthorDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    private Author toAuthor(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }

    // Inyectamos el puerto de la aplicación
    @Value("${server.port}")
    private Integer port;

    @Value("${spring.application.instance-id:default}")
    private String instanceId;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> authorDtos = authorService.findAll();
        return authorDtos;
    }

    @GetMapping(path="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Integer id) {
        AuthorDto authorDto = authorService.findById(id);

        AuthorDto au=new AuthorDto();

        // Modificamos el lastName para incluir el puerto
        if (authorDto != null) {
            String lastName = authorDto.getLastName();
            String updatedLastName = String.format("[%d]-%s", port, lastName);

            au.setId(authorDto.getId());
            au.setFirstName(authorDto.getFirstName());
            au.setLastName(updatedLastName);

        }

        return ResponseEntity.ok(au);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        return ResponseEntity.ok(authorService.save(authorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path="/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Integer id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        return ResponseEntity.ok(authorService.update(authorDto));
    }

}
