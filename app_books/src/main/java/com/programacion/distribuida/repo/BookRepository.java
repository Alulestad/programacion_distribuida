package com.programacion.distribuida.repo;

import com.programacion.distribuida.db.Book;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class BookRepository implements PanacheRepositoryBase<Book,Integer> {
    //@PersistenceContext
    //EntityManager em;

    public Optional<Book> update(Integer id, Book book) {

        var obj=this.findByIdOptional(id);

        if(obj.isEmpty()) {

            return Optional.empty();
        }

        var bookObj=obj.get();

        bookObj.setIsbn(book.getIsbn());
        bookObj.setTitle(book.getTitle());
        bookObj.setPrice(book.getPrice());
        bookObj.setIdAutor(book.getIdAutor());


        return Optional.of(bookObj);


    }
}
