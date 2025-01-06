package com.programacion.distribuida.db;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @GeneratedValue(generator = "seq_book",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1,initialValue = 1,sequenceName = "seq_book", name = "seq_book")
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
