package com.programacion.distribuida.servicios;
import com.programacion.distribuida.db.Persona;

import java.util.List;
import java.util.Optional;

public class ServiciosPersonaImpl implements ServicioPersona {

    @Override
    public void createPersona(Persona persona) {

    }

    @Override
    public Optional<List<Persona>> findPersonas() {
        return Optional.empty();
    }

    @Override
    public Optional<Persona> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void updatePersona(Persona persona) {

    }

    @Override
    public void deletePersona(Integer id) {

    }
}


/*
import io.vertx.ext.bridge.PermittedOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServiciosPersonaImpl implements ServicioPersona {
    @Inject
    EntityManager em;



    @Override
    public void createPersona(Persona persona) {
        em.getTransaction().begin();
            em.persist(persona);
        em.getTransaction().commit();
    }

    @Override
    public Optional<List<Persona>> findPersonas() {
        Query query = em.createQuery("select p FROM Persona p order by p.nombre ASC ", Persona.class);
        List<Persona> ret = query.getResultList();
        return Optional.ofNullable(ret);
    }

    @Override
    public Optional<Persona>  findById(Integer id) {
        Persona persona = em.find(Persona.class, id);
        return Optional.ofNullable(persona);
    }

    @Override
    public void updatePersona(Persona persona) {

        em.getTransaction().begin();
            em.merge(persona);
        em.getTransaction().commit();

    }

    @Override
    public void deletePersona(Integer id) {

        em.getTransaction().begin();
            Persona persona = em.find(Persona.class, id);
            em.remove(persona);
        em.getTransaction().commit();
    }
}
*/
