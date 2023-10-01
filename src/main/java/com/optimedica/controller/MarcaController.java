package com.optimedica.controller;

import com.optimedica.model.Marca;
import com.optimedica.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/marca")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @GetMapping("/listar")
    public ResponseEntity<List<Marca>> listarMarcas(){
        List<Marca> marc = service.listarMarca();
        return new ResponseEntity<List<Marca>>(marc, HttpStatus.OK);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Marca> agragarMarca(@Validated @RequestBody Marca marca){
        Marca marca1 = service.nuevo(marca);
        return new ResponseEntity<Marca>(marca1, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Marca> actualizarMarca(@Validated @RequestBody Marca marca){
        Marca marca2 = service.actualizar(marca);
        return new ResponseEntity<Marca>(marca2, HttpStatus.OK);
    }

    @GetMapping("/listarId/{id_marca}")
    public ResponseEntity<Marca> listarPorId(@Validated @RequestBody @PathVariable("id_marca")Integer id_marca)throws Exception{
        Marca marca3 = service.listaPorId(id_marca);
        if (marca3 == null){
            throw new Exception("No existe ID");
        }
        return new ResponseEntity<Marca>(marca3, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id_marca}")
    public ResponseEntity<Void> aliminarMarca(@Validated @RequestBody @PathVariable("id_marca")Integer id_marca)throws Exception{
        Marca marca4 = service.listaPorId(id_marca);;
        if (marca4 == null){
            throw new Exception("No existe ID");
        }
        service.eliminar(id_marca);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
