package com.optimedica.service;

import com.optimedica.model.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MarcaService {
    public List<Marca> listarMarca();
    public Marca listaPorId(Integer id_marca);
    public Marca nuevo(Marca marca,  MultipartFile imagen);
    public Marca actualizar(Marca marca,  MultipartFile imagen);
    public void eliminar(Integer id_marca);
    public Page<Marca> ListarPaginas(Pageable pageable);
}
