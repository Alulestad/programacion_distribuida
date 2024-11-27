package com.programacion.distribuida.servicios;

import com.programacion.distribuida.db.Persona;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ServiciosPersonaImpl implements ServicioPersona {

    @Inject
    EntityManager em;



    public Persona findById(Integer id){
        return em.find(Persona.class, 1);
    }


}
