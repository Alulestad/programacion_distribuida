package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.dto.AuthorDto;
import com.programacion.distribuida.repo.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
@Tag(name = "Endpoint de autores", description = "Endpoint para conusmir los recursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@ApplicationScoped
//@Transactional
public class AuthorRest {
    @Inject
    AuthorRepository repository;
    //@Inject
    String stringd;

    @GET
    @Operation(summary = "Obtener la lista completa", description = "Devuelve la lista completa de autores")
    @APIResponse(responseCode = "200", description = "Obtencion exitoso de autores")
    @APIResponse(responseCode = "404", description = "No se pudo obtener los autores")
    @Tag(name = "Hello API", description = "Endpoint de prueba para saludar")
    public List<Author> findAll() {
        return repository.findAll().list();
    }

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer port;

    AtomicInteger counter = new AtomicInteger(1);
    //esto es para que funcione en varios hilos y auente en evetos de concurencia.




//    @GET
//    @Path("/{id}")
//    public Response findById(@PathParam("id") Integer id) {
//        System.out.printf("%s: servidor  %d \n", LocalDateTime.now(),port);
//
//        var obj = repository.findByIdOptional(id);
//
//        if(obj.isEmpty()) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//        String txt = String.format("[%d]-%s",port,obj.get().getLastName());
//        //obj.get().setLastName(txt);
//
//        var ret = new AuthorDto();
//        ret.setFirstName(obj.get().getFirstName());
//        ret.setLastName(txt);
//        ret.setId(obj.get().getId());
//
//        //return Response.ok(obj.get()).build();
//        return Response.ok(ret).build();
//    }
    //BORRAR SI _TODO SALE BIEN en el emtodo de a continuacion

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener author", description = "Devuelve un autor especofico")
    @APIResponse(responseCode = "200", description = "Obtencion exitoso de autor")
    @APIResponse(responseCode = "404", description = "Author no encontrado")
    public Response findById(
            @Parameter(
                    name = "id",
                    description = "ID del autor que se desea obtener.",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "1"
            )

            @PathParam("id") Integer id) {
        // Obtener el hostname del contenedor
        String hostname = System.getenv("HOSTNAME");

        // Obtener la IP del contenedor (opcional)
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
//        } catch (nknownHostException e) {
        } catch (UnknownHostException e) {
            ip = "Unknown IP";
        }


        // Imprimir información de la instancia
        System.out.printf("%s: servidor %s (IP: %s, Puerto: %d) \n", LocalDateTime.now(), hostname, ip, port);

        var obj = repository.findByIdOptional(id);

        if (obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }



        // Incluir el hostname y la IP en la respuesta
        String txt = String.format("[%s - %s - %d] %s", hostname, ip, port, obj.get().getLastName());

        var ret = new AuthorDto();
        ret.setFirstName(obj.get().getFirstName());
        ret.setLastName(txt);
        ret.setId(obj.get().getId());

        return Response.ok(ret).build();
    }



    @GET
    @Path("/error/{id}")
    @Operation(summary = "Obtener autor con manejo de errores",
            description = "Devuelve un autor específico pero falla en algunos intentos para probar la tolerancia a fallos.")
    @APIResponse(responseCode = "200", description = "Obtención exitosa de autor")
    @APIResponse(responseCode = "500", description = "Error interno del servidor en intentos fallidos")
    public Response findByIdError(
            @Parameter(
                    name = "id",
                    description = "ID del autor que se desea obtener.",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "1"
            )
            @PathParam("id") Integer id) {
        // Obtener el hostname del contenedor
        String hostname = System.getenv("HOSTNAME");

        // Obtener la IP del contenedor (opcional)
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "Unknown IP";
        }


        int value = counter.getAndIncrement();
        if(value % 5 != 0) {
            String msg=String.format("Intento %d ===> error",value);
            System.out.println("*******************************+"+msg);
            throw new RuntimeException(msg);
        }

        // Imprimir información de la instancia
        System.out.printf("%s: servidor %s (IP: %s, Puerto: %d) \n", LocalDateTime.now(), hostname, ip, port);

        var obj = repository.findByIdOptional(id);

        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        // Incluir el hostname y la IP en la respuesta
        String txt = String.format("[%s - %s - %d] %s", hostname, ip, port, obj.get().getLastName());

        var ret = new AuthorDto();
        ret.setFirstName(obj.get().getFirstName());
        ret.setLastName(txt);
        ret.setId(obj.get().getId());

        return Response.ok(ret).build();
    }

    @POST
    @Operation(summary = "Crear un nuevo autor", description = "Crea un nuevo autor en el sistema.")
    @APIResponse(responseCode = "201", description = "Autor creado exitosamente")
    @APIResponse(responseCode = "500", description = "Error interno del servidor al crear el autor")
    public Response create(
            @Parameter(
                    name = "author",
                    description = "Objeto Author que representa el autor a crear.",
                    required = true,
                    schema = @Schema(implementation = Author.class),
                    example = "{\n" +
                            "  \"firstName\": \"John\",\n" +
                            "  \"lastName\": \"Doe\"\n" +
                            "}"

            )
            Author author) {

        try {
            repository.persist(author);
            return Response.status(Response.Status.CREATED).build();
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar un autor", description = "Actualiza los datos de un autor existente.")
    @APIResponse(responseCode = "200", description = "Autor actualizado exitosamente")
    @APIResponse(responseCode = "404", description = "Autor no encontrado")
    @Parameter(
            name = "id",
            description = "ID del autor que se desea actualizar.",
            required = true,
            in = ParameterIn.PATH,
            example = "1"
    )
    @Parameter(
            name = "author",
            description = "Objeto Author con los datos actualizados del autor.",
            required = true,
            example = "{\n" +
                    "  \"firstName\": \"John\",\n" +
                    "  \"lastName\": \"Doe\"\n" +
                    "}"
    )
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
    @Operation(summary = "Eliminar un autor", description = "Elimina un autor existente por su ID.")
    @APIResponse(responseCode = "200", description = "Autor eliminado exitosamente")
    @APIResponse(responseCode = "404", description = "Autor no encontrado")
    @Parameter(
            name = "id",
            description = "ID del autor que se desea eliminar.",
            required = true,
            in = ParameterIn.PATH,
            example = "1"
    )
    public Response delete(@PathParam("id") Integer id) {
        var obj = repository.findByIdOptional(id);
        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        repository.deleteById(id);
        return Response.ok(obj.get()).build();
    }

}
