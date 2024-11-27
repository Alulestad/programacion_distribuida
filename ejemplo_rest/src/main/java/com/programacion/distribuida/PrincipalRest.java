package com.programacion.distribuida;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programacion.distribuida.db.Persona;
import com.programacion.distribuida.servicios.ServicioPersona;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.inject.spi.CDI;

public class PrincipalRest {

    static void handleAlgo(ServerRequest req, ServerResponse res){
        res.send("Algo");
    }

    static void handleSelectById(ServerRequest req, ServerResponse res) {
        var service=  CDI.current().select(ServicioPersona.class).get();
        Gson gson=  new GsonBuilder().setPrettyPrinting().create();
        var id=Integer.valueOf(req.path().pathParameters().get("id"));
        var persona= service.findById(id);
        res.send(gson.toJson(persona));
    }

    static void handleSelect(ServerRequest req, ServerResponse res) {
        var service=  CDI.current().select(ServicioPersona.class).get();
        Gson gson=  new GsonBuilder().setPrettyPrinting().create();
        //var persona= service.findPersonas();
        res.send(gson.toJson(service.findPersonas()));
    }


    private static void handleInsert(ServerRequest serverRequest, ServerResponse serverResponse) {
        var service=  CDI.current().select(ServicioPersona.class).get();
        Gson gson=  new GsonBuilder().setPrettyPrinting().create();
        String personaStr=serverRequest.content().as(String.class);
        Persona persona= gson.fromJson(personaStr, Persona.class);
        //System.out.println("Persona: "+persona);
        service.createPersona(persona);
        serverResponse.send(gson.toJson(persona));
    }

    private static void handlePut(ServerRequest serverRequest, ServerResponse serverResponse) {
        var service=  CDI.current().select(ServicioPersona.class).get();
        Gson gson=  new GsonBuilder().setPrettyPrinting().create();
        String personaStr=serverRequest.content().as(String.class);
        Persona persona= gson.fromJson(personaStr, Persona.class);
        //System.out.println("Persona: "+persona);
        service.updatePersona(persona);
        serverResponse.send(gson.toJson(persona));
    }

    private static void handleDelete(ServerRequest serverRequest, ServerResponse serverResponse) {
        var service=  CDI.current().select(ServicioPersona.class).get();
        var id=Integer.valueOf(serverRequest.path().pathParameters().get("id"));
        service.deletePersona(id);
        serverResponse.send("Persona eliminada");
    }


    public static void main(String[] args) {
        /*var server= WebServer.builder()
                .port(8080)
                .build()
                .start();

         */
        SeContainer container= SeContainerInitializer
                .newInstance()
                .initialize();


        /*HttpRouting rt = HttpRouting.builder().
                get("/hola", (req, res) -> res.send("Hola mundo")).
                build();
*/



        WebServer.builder()
                .routing(it -> it
                        .get("/algo", PrincipalRest::handleAlgo)
                        .get("/personas", PrincipalRest::handleSelect)
                        .get("/personas/{id}", PrincipalRest::handleSelectById)
                        .post("/personas", PrincipalRest::handleInsert)
                        .put("/personas", PrincipalRest::handlePut)
                        .delete("/personas/{id}", PrincipalRest::handleDelete)
                )
                .port(8080)
                .build()
                .start();

        //container.close();
    }






}