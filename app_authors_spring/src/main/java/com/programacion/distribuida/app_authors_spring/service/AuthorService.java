package com.programacion.distribuida.app_authors_spring.service;

import com.programacion.distribuida.app_authors_spring.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    public List<AuthorDto> findAll();
    public AuthorDto findById(Integer id);
    public AuthorDto save(AuthorDto authorDto);
    public void delete(Integer id);
    public AuthorDto update(AuthorDto authorDto);


}
