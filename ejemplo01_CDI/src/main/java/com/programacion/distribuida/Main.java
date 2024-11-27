package com.programacion.distribuida;


import com.programacion.distribuida.servicios.StringService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            SeContainer container= SeContainerInitializer
                    .newInstance()
                    .initialize();

            StringService servicio= container.select(StringService.class).get();

            System.out.println(servicio);

            var ret= servicio.convertir("Hola cdi");
            System.out.println(ret);
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }//Aca hace un close automatico.
        //me evito poner el finaly porque el finaly tambien
        //puede necesitar un finally




        //container.close();
    }
}