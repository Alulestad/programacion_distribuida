package com.programacion.distribuida.app_books_spring.service;

import com.programacion.distribuida.app_books_spring.dto.AuthorDto;
import com.programacion.distribuida.app_books_spring.dto.BookDto;
import com.programacion.distribuida.app_books_spring.modelo.Book;
import com.programacion.distribuida.app_books_spring.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    //Tranformacion de objetos
    private final ModelMapper modelMapper = new ModelMapper();

    private BookDto toBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    private Book toBook(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }


    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::toBookDto).toList();
    }

    @Override
    public BookDto findById(Integer id) {
        Book book=bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return toBookDto(book);
    }

    @Override
    public BookDto save(BookDto bookDto) {
        Book book=bookRepository.save(toBook(bookDto));
        return toBookDto(book);
    }

    @Override
    public void delete(Integer id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Book not found");
        }
    }

    @Override
    public BookDto update(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + bookDto.getId()));

        // Actualizar solo si los valores han cambiado
        if (!book.getIsbn().equals(bookDto.getIsbn())) {
            book.setIsbn(bookDto.getIsbn());
        }
        if (!book.getTitle().equals(bookDto.getTitle())) {
            book.setTitle(bookDto.getTitle());
        }
        if (!book.getPrice().equals(bookDto.getPrice())) {
            book.setPrice(bookDto.getPrice());
        }
        if (!book.getIdAutor().equals(bookDto.getIdAutor())) {
            book.setIdAutor(bookDto.getIdAutor());
        }

        // Guardar los cambios (save es suficiente para actualización)
        bookRepository.saveAndFlush(book);

        // Convertir la entidad a DTO y retornarlo
        return toBookDto(book);
    }
}
