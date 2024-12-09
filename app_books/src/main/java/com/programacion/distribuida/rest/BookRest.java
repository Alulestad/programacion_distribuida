package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Book;
import com.programacion.distribuida.dto.AuthorDto;
import com.programacion.distribuida.dto.BookDto;
import com.programacion.distribuida.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class BookRest {
    @Inject
    BookRepository repository;

    @Inject
    @ConfigProperty(name="authors.server")
    String authorsServer;

    @GET
    public List<BookDto> findAll() {
        //--version-1 -->original
        //return repository.findAll().list();

        //version-2-->JAX-RS Client
        var client= ClientBuilder.newClient();


        return repository.streamAll()
                .map(book->{
                    //localhost:9090/authors/{id}
            System.out.println("Buscando author con id= " + book.getIdAutor());

            var author= client.target(authorsServer)
                    .path("/authors/{id}")
                    .resolveTemplate("id",book.getIdAutor())
                    .request(MediaType.APPLICATION_JSON)
                    .get(AuthorDto.class);
            var dto = new BookDto();
            dto.setId(book.getId());
            dto.setIsbn(book.getIsbn());
            dto.setTitle(book.getTitle());
            dto.setPrice(book.getPrice());
            dto.setAutorName(author.getFirstName()+" "+author.getLastName());

            return dto;
        }).toList();
        //version-3-->
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        var obj = repository.findByIdOptional(id);

        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(obj.get()).build();
    }

    @POST
    public Response create(Book book) {

        try {
            repository.persist(book);
            return Response.status(Response.Status.CREATED).build();
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Book book) {
        var obj = repository.findByIdOptional(id);
        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        repository.update(id,book);
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
