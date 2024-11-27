package com.programacion.distribuida.servicios;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogServiceImpl implements LogService {


    @Override
    public void log(String msg) {
        System.out.println("LOG: "+msg);
    }
}
