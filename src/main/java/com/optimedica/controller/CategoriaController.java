package com.optimedica.controller;

import com.optimedica.model.Categoria;
import com.optimedica.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/v1")
public class CategoriaController {
    @Autowired
    private CategoriaService service;

    //LISTAR TODA LA CATEGORIA
    @GetMapping("/categoria")
    public ResponseEntity<List<Categoria>> getAllCat(){
        List<Categoria> cat1 = service.listarCat();
        return new ResponseEntity<List<Categoria>>(cat1, HttpStatus.OK);
    }
    //ACTUALIZAR CATEGORIA
    @PutMapping("/categoria/{id_categoria}")
    public ResponseEntity<?> actualizarCategorias(@Validated @RequestBody Categoria categoria, BindingResult result, @PathVariable Integer id_categoria){

        Categoria nuevaCAtegoria = service.listarPorId(id_categoria);
        Categoria actualizarCategoria = null;
        Map<String, Object> response = new HashMap<>();
        //manejo de errores
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        //--------------
        if(nuevaCAtegoria == null){
            response.put("mensaje", "Error: no se pudo editar la categoria ID: ".concat(id_categoria.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        //-------------
        try {
            nuevaCAtegoria.setNombre(categoria.getNombre());

            actualizarCategoria = service.actualizarCat(nuevaCAtegoria);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La categoria ha sido actualizado con éxito!");
        response.put("categoria", actualizarCategoria);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    //LISTAR POR ID
    @GetMapping("/categoria/{id_categoria}")
    public ResponseEntity<?> listarPorID(@PathVariable("id_categoria")Integer id_categoria){
        Categoria categ = null;
        Map<String, Object> response = new HashMap<>();
        //controlando excepciones
        try {
            categ = service.listarPorId(id_categoria);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error en la consulta a la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //
        if(categ == null){
            response.put("mensaje", "Categoria ID: ".concat(id_categoria.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Categoria>(categ, HttpStatus.OK);
    }
    //CREANDO NUEVA CATEGORIA
    @PostMapping("/categoria")
    public ResponseEntity<?> nuevaCateg(@Validated @RequestBody Categoria categoria, BindingResult result) {

        Categoria nuevaCategoria = null;
        Map<String, Object> response = new HashMap<>();
        //manejo de errores
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        //--------
        try {
            nuevaCategoria = service.nuevoCat(categoria);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La categoria ha sido creado con éxito!");
        response.put("categoria", nuevaCategoria);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //eliminar datos del cliente
    @DeleteMapping("/categoria/{id_categoria}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Integer id_categoria){
        Map<String, Object> response = new HashMap<>();

        try {
            service.eliminarCat(id_categoria);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar categoria en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La categoria ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
