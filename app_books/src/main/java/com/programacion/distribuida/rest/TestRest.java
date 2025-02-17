package com.programacion.distribuida.rest;

import io.quarkus.devui.runtime.continuoustesting.ContinuousTestingJsonRPCState;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/test")
@Tag(name = "Endpoint de test", description = "Endpoint de ejemplo")

public class TestRest {

    //para DI
    @Inject
    @ConfigProperty(name="quarkus.http.port")
    private Integer port;


    @GET
    public String test(){
        Config cfg= ConfigProvider.getConfig();

        cfg.getConfigSources()
                .forEach(
                        c-> System.out.printf("%d -%s\n",c.getOrdinal(),c.getName())
                );

        var msg = cfg.getValues("ejemplo.mensaje",String.class);

        return String.format("test [%d]: %s",port, msg);
    }
}
