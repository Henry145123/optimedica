package com.optimedica.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimedica.model.Cliente;
import com.optimedica.model.Producto;
import com.optimedica.service.ClienteService;
import com.optimedica.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> clientes = service.listarCliente();
        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Cliente> agragarCliente(@RequestParam("cliente")  String cliente
                                                   ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Cliente clienteObj = objectMapper.readValue(cliente, Cliente.class);

        Cliente clien = service.nuevo(clienteObj);
        return new ResponseEntity<Cliente>(clien, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Cliente> actualizarCliente(@RequestParam("cliente")  String cliente)throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Cliente clienteObj = objectMapper.readValue(cliente, Cliente.class);

        Cliente clien = service.nuevo(clienteObj);
        return new ResponseEntity<Cliente>(clien, HttpStatus.OK);
    }

    @GetMapping("/listarId/{id_cliente}")
    public ResponseEntity<Cliente> listarPorId(@Validated @RequestBody @PathVariable("id_cliente")Integer id_cliente)throws Exception{
        Cliente clien = service.listaPorId(id_cliente);
        if (clien == null){
            throw new Exception("No existe ID");
        }
        return new ResponseEntity<Cliente>(clien, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id_cliente}")
    public ResponseEntity<Void> aliminarProducto(@Validated @RequestBody @PathVariable("id_cliente")Integer id_cliente)throws Exception{
        Cliente clien = service.listaPorId(id_cliente);;
        if (clien == null){
            throw new Exception("No existe ID");
        }
        service.eliminar(id_cliente);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
