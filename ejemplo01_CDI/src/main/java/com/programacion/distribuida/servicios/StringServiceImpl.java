package com.programacion.distribuida.servicios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

//lo hace componente CDI
@ApplicationScoped
public class StringServiceImpl implements StringService {

    @Inject
    LogService log;

    @Override
    public String convertir(String txt) {
        log.log("Converting <<"+txt+">>");
        return txt.toUpperCase();
    }
}
