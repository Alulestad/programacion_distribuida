package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Persona;
import com.programacion.distribuida.servicios.PersonaRepository;
import com.programacion.distribuida.servicios.ServicioPersona;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/personas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class PersonaRest {

    @Inject
    //ServicioPersona servicioPersona;
    PersonaRepository personaRepository;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id){
        var obj = personaRepository.findById(id);

        if(obj==null ){// OPTIONSAL|| obj){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();


    }

    @GET
    public Optional<List<Persona>> findAll(){
        //var obj=personaRepository.streamAll().toList();
        var obj=personaRepository
                .findAll(Sort.ascending("id"))
                .list();
        return Optional.ofNullable(obj);
    }

    @POST
    public Response create(Persona persona){

        if(persona==null){
            return Response.status(Response.Status.NOT_FOUND).build();

        }
        personaRepository.persist(persona);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id,Persona persona){
        /*var obj = personaRepository.findByIdOptional(id);
        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();

        }

        var perObj=obj.get();
        perObj.setNombre(persona.getNombre());
        perObj.setDireccion(persona.getDireccion());


*/
        var obj = personaRepository.findByIdOptional(id);
        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();

        }
        personaRepository.update(id,persona);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id){
        var obj = personaRepository.findByIdOptional(id);
        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();

        }
        personaRepository.delete(obj.get());

        return Response.ok().build();
    }
}
