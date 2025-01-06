package com.programacion.distribuida.app_books_spring.rest;

import com.programacion.distribuida.app_books_spring.dto.AuthorDto;
import com.programacion.distribuida.app_books_spring.dto.BookDto;
import com.programacion.distribuida.app_books_spring.modelo.Book;
import com.programacion.distribuida.app_books_spring.repository.BookRepository;
import com.programacion.distribuida.app_books_spring.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@CrossOrigin

public class BookRestController {

    //Tranformacion de objetos
    private final ModelMapper modelMapper = new ModelMapper();

    private BookDto toBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    private Book toBook(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }



    private final BookService bookService;
    private final AuthorClientController authorClientController;

    public BookRestController(BookService bookService, AuthorClientController authorClientController) {
        this.bookService = bookService;
        this.authorClientController = authorClientController;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> findAll() {
        return bookService.findAll().stream().map(book -> {
            AuthorDto author = authorClientController.getAuthorsFromLoadBalancedInstance(book.getIdAutor());
            BookDto dto = new BookDto();
            dto.setId(book.getId());
            dto.setIsbn(book.getIsbn());
            dto.setTitle(book.getTitle());
            dto.setPrice(book.getPrice());
            dto.setAutorName(author.getFirstName() + " " + author.getLastName());
            dto.setIdAutor(author.getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping(path ="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> findById(@PathVariable Integer id) {
        BookDto bookDto = bookService.findById(id);

        return ResponseEntity.ok(bookDto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity.ok(toBook(bookService.save(toBookDto(book))));
    }

    @PutMapping(path="/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> update(@PathVariable Integer id, @RequestBody Book book) {
        book.setId(id);
        return ResponseEntity.ok(toBook(bookService.save(toBookDto(book))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        BookDto bookDto = bookService.findById(id);
        if (bookDto != null) {
            bookService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}