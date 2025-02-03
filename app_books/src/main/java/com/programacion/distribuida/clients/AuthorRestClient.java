package com.programacion.distribuida.clients;

import com.programacion.distribuida.dto.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@RegisterRestClient(configKey = "authors-api")
@RegisterRestClient(baseUri = "stork://app-authors-stork")
public interface AuthorRestClient {

    @GET
    @Path("/{id}")
    Response findById(@PathParam("id") Integer id) ;

    @GET
    @Path("/error/{id}")
    @Retry(maxRetries=3)
    @Fallback(fallbackMethod = "findByIdFallback")
    Response findByIdError(@PathParam("id") Integer id) ;

    default Response findByIdFallback(Integer id) {
        AuthorDto ret = new AuthorDto();
        ret.setId(-1);
        ret.setFirstName("noname");
        ret.setLastName("");

        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(ret)
                .build();
    }


    @GET
    public List<AuthorDto> findAll();


}
