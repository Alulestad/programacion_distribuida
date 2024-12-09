package com.programacion.distribuida.repo;

import com.programacion.distribuida.db.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class AuthorRepository implements PanacheRepositoryBase<Author,Integer> {

    public Optional<Author> update(Integer id, Author author) {

        var obj=this.findByIdOptional(id);

        if(obj.isEmpty()) {

            return Optional.empty();
        }

        var authorObj=obj.get();

        authorObj.setFirstName(author.getFirstName());
        authorObj.setLastName(author.getLastName());

        return Optional.of(authorObj);


    }
}
