package com.optimedica.service;

import com.optimedica.model.Marca;

import java.util.List;

public interface MarcaService {
    public List<Marca> listarMarca();
    public Marca listaPorId(Integer id_marca);
    public Marca nuevo(Marca marca);
    public Marca actualizar(Marca marca);
    public void eliminar(Integer id_marca);
}
