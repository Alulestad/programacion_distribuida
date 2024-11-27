package com.programacion.distribuida;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programacion.distribuida.db.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class PrincipalJpa {



    public static void main(String[] args) {
        EntityManagerFactory emf=
        Persistence.createEntityManagerFactory("pu-distribuida");

        EntityManager em=emf.createEntityManager();


        Persona p= new Persona();
        p.setNombre("Daniel");
        p.setDireccion("Sangolqui");
        em.getTransaction().begin();
            em.persist(p);
        em.getTransaction().commit();



        Query query=em.createQuery("select p FROM Persona p order by p.nombre ASC ", Persona.class);



        List <Persona> ret=query.getResultList();

        Gson gson=new GsonBuilder()
                .setPrettyPrinting()
                .create();

        var jsonList=ret.stream()
                        //.map(persona->gson.toJson(persona));
                                .map(gson::toJson);

        jsonList.forEach(System.out::println);
        System.out.println("///////////////////////////////");


        ret.forEach(System.out::println);

        System.out.println("///////////////////////////////");
        Persona persona=em.find(Persona.class, 1);
        System.out.println(persona);
        em.close();
        emf.close();
    }
}
