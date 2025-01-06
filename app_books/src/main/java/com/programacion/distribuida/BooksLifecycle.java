package com.programacion.distribuida;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.consul.ConsulClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
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
