package com.programacion.distribuida.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "author")
@Data
public class Author {
    @Id
    @GeneratedValue(generator = "author_id_seq",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1,initialValue = 1,sequenceName = "author_id_seq", name = "author_id_seq")
    private Integer id;

    @Column(name = "auth_first_name")
    private String firstName;

    @Column(name = "auth_last_name")
    private String lastName;


}
