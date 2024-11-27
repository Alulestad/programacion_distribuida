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
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("pu-distribuida");
        EntityManager em = emf.createEntityManager();

        //Insercion de personas
        System.out.println("########### PERSIST ###########");
        Persona p = new Persona();
        p.setNombre("Daniel");
        p.setDireccion("Sangolqui");
        //transaccion
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        //select
        System.out.println("########### SELECT ###########");
        Query query = em.createQuery("select p FROM Persona p order by p.nombre ASC ", Persona.class);
        List<Persona> ret = query.getResultList();
        ret.forEach(System.out::println);

        //uso de Gson
        System.out.println("########### JSON ###########");
        Gson gson=new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //transformo las personas al formato Json
        var jsonList=ret.stream()
                        //.map(persona->gson.toJson(persona));
                                .map(gson::toJson);
        jsonList.forEach(System.out::println);

        System.out.println("########### FIND ###########");
        Persona persona=em.find(Persona.class, 1);
        System.out.println(persona);




        em.close();
        emf.close();
    }
}
