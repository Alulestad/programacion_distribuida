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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/books")
@Tag(name = "Endpoint de books", description = "Endpoint para conusmir los recursos")
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
    @Operation(summary = "Obtener la lista completa", description = "Devuelve la lista completa de books")
    @APIResponse(responseCode = "200", description = "Obtencion exitoso de books")
    @APIResponse(responseCode = "404", description = "No se pudo obtener los books")
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
    @Operation(summary = "Obtener la lista completa", description = "Devuelve la lista completa de books con author")
    @APIResponse(responseCode = "200", description = "Obtencion exitoso de books")
    @APIResponse(responseCode = "404", description = "No se pudo obtener los books")
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
    @Operation(summary = "Obtener books con manejo de errores",
            description = "Devuelve books pero con falla en algunos intentos para probar la tolerancia a fallos.")
    @APIResponse(responseCode = "200", description = "Obtención exitosa de books")
    @APIResponse(responseCode = "500", description = "Error interno del servidor en intentos fallidos")
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
    @Operation(summary = "Obtener book", description = "Devuelve un book especofico")
    @APIResponse(responseCode = "200", description = "Obtencion exitoso de book")
    @APIResponse(responseCode = "404", description = "Book no encontrado")
     public Response findById(
            @Parameter(
                    name = "id",
                    description = "ID del book que se desea obtener.",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "1"
            )
            @PathParam("id") Integer id) {
        var obj = repository.findByIdOptional(id);

        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(obj.get()).build();
    }

    @GET
    @Path("/error/{id}")
    @Operation(summary = "Obtener book con manejo de errores",
            description = "Devuelve book pero con falla en algunos intentos para probar la tolerancia a fallos.")
    @APIResponse(responseCode = "200", description = "Obtención exitosa de book")
    @APIResponse(responseCode = "500", description = "Error interno del servidor en intentos fallidos")
    public Response findByIdError(
            @Parameter(
                    name = "id",
                    description = "ID del book que se desea obtener.",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "1"
            )
            @PathParam("id") Integer id) {
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
    @Operation(summary = "Crear un nuevo book", description = "Crea un nuevo book en el sistema.")
    @APIResponse(responseCode = "201", description = "Book creado exitosamente")
    @APIResponse(responseCode = "500", description = "Error interno del servidor al crear el book")
    public Response create(
            @Parameter(
                    name = "book",
                    description = "Objeto book que representa el book a crear.",
                    required = true,
                    example = "{\n" +
                            "  \"isbn\": \"123456789\",\n" +
                            "  \"title\": \"El libro\",\n" +
                            "  \"price\": 100.0,\n" +
                            "  \"idAutor\": 1\n" +
                            "}"

            )
            Book book) {

        try {
            repository.persist(book);
            return Response.status(Response.Status.CREATED).build();
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar un book", description = "Actualiza los datos de un book existente.")
    @APIResponse(responseCode = "200", description = "book actualizado exitosamente")
    @APIResponse(responseCode = "404", description = "book no encontrado")
    @Parameter(
            name = "id",
            description = "ID del book que se desea actualizar.",
            required = true,
            in = ParameterIn.PATH,
            example = "1"
    )
    @Parameter(
            name = "book",
            description = "Objeto Book con los datos actualizados del book.",
            required = true,
            example = "{\n" +
                    "  \"isbn\": \"123456789\",\n" +
                    "  \"title\": \"El libro\",\n" +
                    "  \"price\": 100.0,\n" +
                    "  \"idAutor\": 1\n" +
                    "}"
    )
    public Response update(@PathParam("id") Integer id, Book book) {
        var obj = repository.findByIdOptional(id);
        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        repository.update(id,book);
        return Response.ok(obj.get()).build();
    }

    @DELETE
    @Operation(summary = "Eliminar un book", description = "Elimina un book existente por su ID.")
    @APIResponse(responseCode = "200", description = "Book eliminado exitosamente")
    @APIResponse(responseCode = "404", description = "Book no encontrado")
    @Parameter(
            name = "id",
            description = "ID del book que se desea eliminar.",
            required = true,
            in = ParameterIn.PATH,
            example = "1"
    )
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
