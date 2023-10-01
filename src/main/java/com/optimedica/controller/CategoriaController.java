package com.optimedica.controller;

import com.optimedica.model.Categoria;
import com.optimedica.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService service;

    @GetMapping("/listar")
    public ResponseEntity<List<Categoria>> listar(){
        List<Categoria> cat1 = service.listarCat();
        return new ResponseEntity<List<Categoria>>(cat1, HttpStatus.OK);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Categoria> agregar(@Validated @RequestBody Categoria categoria){
        Categoria cat2 = service.nuevoCat(categoria);
        return new ResponseEntity<Categoria>(cat2, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Categoria> actualizar(@Validated @RequestBody Categoria categoria){
        Categoria cat3 = service.actualizarCat(categoria);
        return new ResponseEntity<Categoria>(cat3, HttpStatus.OK);
    }

    @GetMapping("/buscarPorId/{id_categoria}")
    public ResponseEntity<Categoria> buscarPorId(@Validated @RequestBody @PathVariable("id_categoria")Integer id_categoria) throws Exception{
        Categoria cat4 = service.listarPorId(id_categoria);
        if (cat4 == null){
            throw new Exception("No existe Id");
        }
        return new ResponseEntity<Categoria>(cat4, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarPorId/{id_categoria}")
    public ResponseEntity<Void> eliminarPorId(@Validated @RequestBody @PathVariable("id_categoria")Integer id_categoria)throws Exception{
        Categoria cat5 = service.listarPorId(id_categoria);
        if (cat5 == null){
            throw new Exception("No existe Id");
        }
        service.eliminarCat(id_categoria);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
