package com.optimedica.service.implement;

import com.optimedica.model.Cliente;
import com.optimedica.model.Producto;
import com.optimedica.repository.ClienteRepository;
import com.optimedica.repository.ProductoRepository;
import com.optimedica.service.ClienteService;
import com.optimedica.service.ImagenService;
import com.optimedica.service.ProductoService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ImagenService imagenService;
    @Override
    public List<Cliente> listarCliente() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Cliente listaPorId(Integer id_cliente) {
        return repository.findById(id_cliente).orElse(null);
    }

    @Override
    @Transactional
    public Cliente nuevo(Cliente cliente) {

        Cliente clien = repository.save(cliente);

        return clien;
    }

    @Override
    public Cliente actualizar(Cliente cliente) {



        Cliente clien = repository.saveAndFlush(cliente);

        return clien;
    }

    @Override
    @Transactional
    public void eliminar(Integer id_cliente) {
       Cliente clienteEliminado = listaPorId(id_cliente);
       if(clienteEliminado!=null){
           repository.deleteById(id_cliente);

       }

    }
}
