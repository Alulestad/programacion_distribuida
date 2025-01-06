package com.programacion.distribuida.app_authors_spring.repository;

import com.programacion.distribuida.app_authors_spring.repository.modelo.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
