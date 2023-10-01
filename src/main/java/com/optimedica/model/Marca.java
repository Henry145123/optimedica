package com.optimedica.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marca")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_marca;

    @NotEmpty(message = "El campo no puede estar vacio")
    @Size(min = 4, max = 50, message = "el tamaño debe contener entre 4 y 50 caracteres")
    @Column(nullable = false)
    private String nombre;

    private String imagen;

}
