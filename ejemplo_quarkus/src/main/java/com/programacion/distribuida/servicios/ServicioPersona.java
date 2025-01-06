package com.programacion.distribuida.servicios;

import com.programacion.distribuida.db.Persona;

import java.util.List;
import java.util.Optional;

public interface ServicioPersona {


    //CRUD
    public void createPersona(Persona persona);
    public Optional<List<Persona>> findPersonas();
    public Optional<Persona>  findById(Integer id);
    public void updatePersona(Persona persona);
    public void deletePersona(Integer id);


}
