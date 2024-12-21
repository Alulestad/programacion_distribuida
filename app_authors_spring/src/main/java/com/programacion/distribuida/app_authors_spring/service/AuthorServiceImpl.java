package com.programacion.distribuida.app_authors_spring.service;

import com.programacion.distribuida.app_authors_spring.dto.AuthorDto;
import com.programacion.distribuida.app_authors_spring.repository.AuthorRepository;
import com.programacion.distribuida.app_authors_spring.repository.modelo.Author;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //Tranformacion de objetos
    private final ModelMapper modelMapper = new ModelMapper();

    private AuthorDto toAuthorDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    private Author toAuthor(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }


    @Override
    public List<AuthorDto> findAll() {
        List<Author> authors= authorRepository.findAll();
        return authors.stream().map(this::toAuthorDto).toList();

    }

    @Override
    public AuthorDto findById(Integer id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        return toAuthorDto(author);
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        Author author=authorRepository.save(toAuthor(authorDto));
        return toAuthorDto(author);
    }

    @Override
    public void delete(Integer id) {
        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Author not found");
        }
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        // Verificar si el autor existe, si no lanzar una excepción personalizada
        Author author = authorRepository.findById(authorDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + authorDto.getId()));

        // Actualizar solo si los valores han cambiado
        if (!author.getFirstName().equals(authorDto.getFirstName())) {
            author.setFirstName(authorDto.getFirstName());
        }
        if (!author.getLastName().equals(authorDto.getLastName())) {
            author.setLastName(authorDto.getLastName());
        }

        // Guardar los cambios (save es suficiente para actualización)
        authorRepository.saveAndFlush(author);

        // Convertir la entidad a DTO y retornarlo
        return toAuthorDto(author);
    }
}
