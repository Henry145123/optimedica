package com.optimedica.service;

import com.optimedica.model.Cliente;
import com.optimedica.model.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClienteService {

    public List<Cliente> listarCliente();
    public Cliente listaPorId(Integer id_cliente);
    public Cliente nuevo(Cliente cliente);
    public Cliente actualizar(Cliente cliente);
    public void eliminar(Integer id_producto);
}
