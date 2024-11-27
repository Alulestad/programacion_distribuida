package com.programacion.distribuida.servicios;

import com.programacion.distribuida.db.Persona;

import java.util.List;

public interface ServicioPersona {


    //CRUD
    public void createPersona(Persona persona);
    public List<Persona> findPersonas();
    public Persona findById(Integer id);
    public void updatePersona(Persona persona);
    public void deletePersona(Integer id);


}
