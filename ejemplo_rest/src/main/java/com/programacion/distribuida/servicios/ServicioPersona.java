package com.programacion.distribuida.servicios;

import com.programacion.distribuida.db.Persona;

public interface ServicioPersona {

    public Persona findById(Integer id);
}
