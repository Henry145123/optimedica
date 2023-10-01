package com.optimedica.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;
    private String nombre;



    private String descripcion;
    private String imagen;
    private BigDecimal precio;
    private Integer descuento;
    private Integer stock;
    private String genero;
    private String forma;
    private String color;
    private String material;
    private Timestamp fecha;

    @ManyToOne
    @JoinColumn(name = "id_categoria",referencedColumnName = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_marca",referencedColumnName = "id_marca")
    private Marca marca;

}
