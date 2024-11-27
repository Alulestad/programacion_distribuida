package com.programacion.distribuida;

import com.programacion.distribuida.servicios.ServicioPersona;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.inject.spi.CDI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PrincipalRest {

    static void handleHello(ServerRequest req, ServerResponse res){

        var servicio= CDI.current().select(ServicioPersona.class).get();




        var persona= servicio.findById(1);

        res.send(persona);
    }

    static void handleAlgo(ServerRequest req, ServerResponse res){
        res.send("Algo");
    }



    public static void main(String[] args) {
        /*var server= WebServer.builder()
                .port(8080)
                .build()
                .start();

         */

        HttpRouting routing=HttpRouting.builder()
                .get("/hello", (req, res) -> res.send("Hello World!"))
                .build();

        SeContainer container= SeContainerInitializer
                .newInstance()
                .initialize();

        WebServer.builder()
                .routing(it -> it
                        .get("/hello", (req, res) -> res.send("Hello World!")))
                .port(8080)
                .build()
                .start();

    }
}