package com.programacion.distribuida.app_books_spring.repository;
import com.programacion.distribuida.app_books_spring.modelo.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}