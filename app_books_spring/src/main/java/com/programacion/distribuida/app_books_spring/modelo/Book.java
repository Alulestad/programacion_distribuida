package com.programacion.distribuida.app_books_spring.modelo;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "seq_book", allocationSize = 1)
    private Integer id;

    @Column(name = "book_isbn")
    private String isbn;

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_price")
    private BigDecimal price;

    @Column(name = "book_id_author")
    private Integer idAutor;
}