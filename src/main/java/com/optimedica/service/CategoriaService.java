package com.optimedica.service;

import com.optimedica.model.Categoria;

import java.util.List;

public interface CategoriaService {
    public List<Categoria> listarCat();
    public Categoria listarPorId(Integer id_categoria);
    public Categoria nuevoCat(Categoria categoria);
    public Categoria actualizarCat(Categoria categoria);
    public void eliminarCat(Integer id_categoria);
}
