package com.optimedica.controller;

import com.optimedica.model.Categoria;
import com.optimedica.model.Marca;
import com.optimedica.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/v1")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @GetMapping("/marca")
    public ResponseEntity<List<Marca>> listarMarcas(){
        List<Marca> marc = service.listarMarca();
        return new ResponseEntity<List<Marca>>(marc, HttpStatus.OK);
    }

    //ACTUALIZAR MARCA
    @PutMapping("/marca/{id_marca}")
    public ResponseEntity<?> actualizarMarca(@Valid @RequestBody Marca marca, BindingResult result, @PathVariable Integer id_marca){

        Marca nuevaMarca = service.listaPorId(id_marca);
        Marca actualizarMarca = null;
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
        if(nuevaMarca == null){
            response.put("mensaje", "Error: no se pudo editar la marca ID: ".concat(id_marca.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        //-------------
        try {
            nuevaMarca.setNombre(marca.getNombre());
            nuevaMarca.setImagen(marca.getImagen());

            actualizarMarca = service.actualizar(nuevaMarca);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La categoria ha sido actualizado con éxito!");
        response.put("categoria", actualizarMarca);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @PostMapping("/marca")
    public ResponseEntity<Marca> agragarMarca(@Validated @RequestBody Marca marca){
        Marca marca1 = service.nuevo(marca);
        return new ResponseEntity<Marca>(marca1, HttpStatus.OK);
    }
    //LISTAR POR ID
    @GetMapping("/marca/{id_marca}")
    public ResponseEntity<?> listarPorID(@PathVariable("id_marca")Integer id_marca){
        Marca marc = null;
        Map<String, Object> response = new HashMap<>();
        //controlando excepciones
        try {
            marc = service.listaPorId(id_marca);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error en la consulta a la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //
        if(marc == null){
            response.put("mensaje", "Marca ID: ".concat(id_marca.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Marca>(marc, HttpStatus.OK);
    }
    //CREANDO NUEVA MARCA
    @PostMapping("/marca")
    public ResponseEntity<?> nuevaMarc(@Valid @RequestBody Marca marca, BindingResult result) {
        Marca nuevaMarca = null;
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
            nuevaMarca = service.nuevo(marca);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La marca ha sido creado con éxito!");
        response.put("marca", nuevaMarca);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //eliminar datos de la marca
    @DeleteMapping("/marca/{id_marca}")
    public ResponseEntity<?> eliminarMarca(@PathVariable Integer id_marca){
        Map<String, Object> response = new HashMap<>();

        try {
            service.eliminar(id_marca);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar categoria en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La marca ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
