package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.repo.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class AuthorRest {
    @Inject
    AuthorRepository repository;
    //@Inject
    String stringd;

    @GET
    public List<Author> findAll() {
        return repository.findAll().list();
    }

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer port;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        System.out.printf("%s: servidor  %d \n", LocalDateTime.now(),port);

        var obj = repository.findByIdOptional(id);

        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String txt = String.format("[%d]-%s",port,obj.get().getFirstName());
        obj.get().setLastName(txt);

        var ret = new Author();
        ret.setFirstName(obj.get().getFirstName());
        ret.setLastName(txt);
        ret.setId(obj.get().getId());

        //return Response.ok(obj.get()).build();
        return Response.ok(ret).build();
    }

    @POST
    public Response create(Author author) {

        try {
            repository.persist(author);
            return Response.status(Response.Status.CREATED).build();
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Author author) {
        var obj = repository.findByIdOptional(id);
        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        repository.update(id,author);
        return Response.ok(obj.get()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        var obj = repository.findByIdOptional(id);
        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        repository.deleteById(id);
        return Response.ok(obj.get()).build();
    }

}
