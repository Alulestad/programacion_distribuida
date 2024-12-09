package com.programacion.distribuida.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AuthorDto {

    private Integer id;
    private String firstName;
    private String lastName;
}
