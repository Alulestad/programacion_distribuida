package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.dto.AuthorDto;
import com.programacion.distribuida.repo.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
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
    public Response findById(@PathParam("id") Integer id) {
        // Obtener el hostname del contenedor
        String hostname = System.getenv("HOSTNAME");

        // Obtener la IP del contenedor (opcional)
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
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
    public Response findByIdError(@PathParam("id") Integer id) {
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
