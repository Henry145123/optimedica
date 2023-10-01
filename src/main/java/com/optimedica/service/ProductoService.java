package com.optimedica.service;

import com.optimedica.model.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {

    public List<Producto> listarProducto();
    public Producto listaPorId(Integer id_producto);
    public Producto nuevo(Producto producto, MultipartFile imagen);
    public Producto actualizar(Producto producto, MultipartFile imagen);
    public void eliminar(Integer id_producto);
}
