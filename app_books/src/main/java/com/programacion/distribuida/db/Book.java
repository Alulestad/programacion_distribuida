package com.programacion.distribuida.db;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @GeneratedValue(generator = "book_id_seq",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1,initialValue = 1,sequenceName = "book_id_seq", name = "book_id_seq")
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
