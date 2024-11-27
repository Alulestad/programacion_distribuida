package com.programacion.distribuida.servicios;

import com.programacion.distribuida.db.Persona;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

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
    public List<Persona> findPersonas() {
        Query query = em.createQuery("select p FROM Persona p order by p.nombre ASC ", Persona.class);
        List<Persona> ret = query.getResultList();
        return ret;
    }

    @Override
    public Persona findById(Integer id) {
        return em.find(Persona.class, id);
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
