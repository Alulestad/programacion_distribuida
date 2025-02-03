package com.programacion.distribuida.rest;

import com.programacion.distribuida.clients.AuthorRestClient;
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
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class BookRest {
    @Inject
    BookRepository repository;

    //para version 3
    /*@Inject
    @ConfigProperty(name="authors.server")
    String authorsServer;*/
    @Inject
    @RestClient
    AuthorRestClient client;


    @GET
    public List<BookDto> findAll() {
        //--version-1 -->original
        //return repository.findAll().list();

        //version-2-->JAX-RS Client
        /*var client= ClientBuilder.newClient();


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
        }).toList();*/
        //version-3--> MP client manual
        /*AuthorRestClient client= RestClientBuilder.newBuilder()
                .baseUri(authorsServer)
                .build(AuthorRestClient.class);

        return repository.streamAll()
                .map(book->{
                    //localhost:9090/authors/{id}
                    System.out.println("Buscando author con id= " + book.getIdAutor());

                    var author= client.findById(book.getIdAutor());
                    var dto = new BookDto();
                    dto.setId(book.getId());
                    dto.setIsbn(book.getIsbn());
                    dto.setTitle(book.getTitle());
                    dto.setPrice(book.getPrice());
                    dto.setAutorName(author.getFirstName()+" "+author.getLastName());

                    return dto;
                }).toList();*/
        //obs. quitar el @RegisterRestClient(configKey = "authors-mpi")

        //version-4--> MP client automatica

        return repository.streamAll()
                .map(book->{
                    //localhost:9090/authors/{id}
                    //System.out.println("Buscando author con id= " + book.getIdAutor());

                    //var author= client.findById(book.getIdAutor());
                    var dto = new BookDto();
                    dto.setId(book.getId());
                    dto.setIsbn(book.getIsbn());
                    dto.setTitle(book.getTitle());
                    dto.setPrice(book.getPrice());
                    //dto.setAutorName(author.getFirstName()+" "+author.getLastName());

                    return dto;
                }).toList();
    }

    @GET
    @Path("/todos")
    public List<BookDto> findAllBasic() {
        return repository.streamAll()
                .map(book->{
                    //localhost:9090/authors/{id}
                    System.out.println("Buscando author con id= " + book.getIdAutor());
                    //System.out.println("client: " + client.toString());
                    Response authorResponce = client.findById(book.getIdAutor());
                    AuthorDto author = authorResponce.readEntity(AuthorDto.class);
                    System.out.println(">>>>>> au: "+author);
                    var dto = new BookDto();
                    dto.setId(book.getId());
                    dto.setIsbn(book.getIsbn());
                    dto.setTitle(book.getTitle());
                    dto.setPrice(book.getPrice());
                    dto.setAutorName(author.getFirstName()+" "+author.getLastName());

                    return dto;
                }).toList();
    }

    @GET
    @Path("/todosError")
    public List<BookDto> findAllError() {
        return repository.streamAll()
                .map(book->{
                    //localhost:9090/authors/{id}
                    System.out.println("Buscando author con id= " + book.getIdAutor());
                    //System.out.println("client: " + client.toString());
                    Response authorResponce= client.findByIdError(book.getIdAutor());
                    AuthorDto author= authorResponce.readEntity(AuthorDto.class);
                    System.out.println(">>>>> au: "+author);
                    var dto = new BookDto();
                    dto.setId(book.getId());
                    dto.setIsbn(book.getIsbn());
                    dto.setTitle(book.getTitle());
                    dto.setPrice(book.getPrice());
                    dto.setAutorName(author.getFirstName()+" "+author.getLastName());

                    return dto;
                }).toList();
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

    @GET
    @Path("/error/{id}")
    public Response findByIdError(@PathParam("id") Integer id) {
        var obj = repository.findByIdOptional(id);

        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var libro=obj.get();

        Response authorResponce= client.findByIdError(libro.getIdAutor());
        AuthorDto author= authorResponce.readEntity(AuthorDto.class);

        var dto = new BookDto();
        dto.setId(libro.getId());
        dto.setAutorName(author.getFirstName().concat(" ").concat(author.getLastName()));
        dto.setIsbn(libro.getIsbn());
        dto.setPrice(libro.getPrice());
        dto.setTitle(libro.getTitle());


        return Response.ok(dto).build();
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
