package com.programacion.distribuida.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "persona")
@Data
public class Persona {
    @Id
    @GeneratedValue(generator = "seq_persona",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1,initialValue = 1,sequenceName = "seq_persona", name = "seq_persona")
    private Integer id;

    private String nombre;

    private String direccion;



}
