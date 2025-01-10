package com.programacion.distribuida;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.consul.ConsulClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BooksLifecycle { //Para el servicio OJO esto se añadio luego

    @Inject
    @ConfigProperty(name = "consul.host",defaultValue = "127.0.0.1")
    String consulIp;

    @Inject
    @ConfigProperty(name = "consul.port",defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer appPort;

    String serviceId;
    public void init(@Observes StartupEvent event, Vertx vertx) throws Exception {
        System.out.println("Book service is starting...");

        ConsulClient client = ConsulClient.create(vertx,
                new ConsulClientOptions()
                        .setHost(consulIp)
                        .setPort(consulPort));

        serviceId= UUID.randomUUID().toString();
        var ipAdress= InetAddress.getLocalHost();

        client.registerServiceAndAwait(
                new ServiceOptions()
                        .setName("app-books")
                        .setId(serviceId)
                        .setAddress(ipAdress.getHostAddress())
                        .setPort(appPort)
                        .setTags(List.of(
                                "traefik.enable=true",
                                "traefik.http.routers.routers-app-books.rule=PathPrefix(`/app-books`)",
                                "traefik.http.routers.routers-app-books.middlewares=middleware-books",
                                "traefik.http.middlewares.middleware-books.stripPrefix.prefixes=/app-books"
                                )
                        )
                        .setCheckOptions(
                                new CheckOptions()
                                        //http://10.20.1.45:8080/q/health/live
                                        .setHttp("http://"+ipAdress.getHostAddress()+":"+appPort+"/q/health/live")
                                        .setInterval("5s")
                                        .setDeregisterAfter("10s")
                        )

        );

    }

    public void stop(@Observes ShutdownEvent event, Vertx vertx) throws Exception {
        System.out.println("Book service is stopping...");

        ConsulClient client=ConsulClient.create(
                vertx,
                new ConsulClientOptions()
                .setHost(consulIp)
                .setPort(consulPort)
        );

        client.deregisterServiceAndAwait(serviceId);
    }
}
