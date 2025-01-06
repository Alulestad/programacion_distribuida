package com.programacion.distribuida.app_books_spring.service;

import com.programacion.distribuida.app_books_spring.dto.AuthorDto;
import com.programacion.distribuida.app_books_spring.dto.BookDto;

import java.util.List;

public interface BookService {

        public List<BookDto> findAll();
        public BookDto findById(Integer id);
        public BookDto save(BookDto bookDto);
        public void delete(Integer id);
        public BookDto update(BookDto bookDto);


}
