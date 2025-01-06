package com.programacion.distribuida.app_authors_spring.repository.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "author")
@Data
public class Author {

    @Id
    @GeneratedValue(generator = "seq_author", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1, name = "seq_author", sequenceName = "seq_author",initialValue = 1)
    private Integer id;

    @Column(name = "auth_first_name")
    private String firstName;

    @Column(name = "auth_last_name")
    private String lastName;

}
