package com.optimedica.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimedica.model.Marca;
import com.optimedica.service.FileMarcaService;
import com.optimedica.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/v1")
public class MarcaController {

    @Autowired
    private MarcaService service;
    @Autowired
    private FileMarcaService fileMarcaService;

    @GetMapping("/marca")
    public ResponseEntity<List<Marca>> listarMarcas(){
        List<Marca> marc = service.listarMarca();
        return new ResponseEntity<List<Marca>>(marc, HttpStatus.OK);
    }
    //CREANDO NUEVA MARCA
    @PostMapping("/marca")
    public ResponseEntity<Marca> create(
            @RequestParam("marca") String marca,
            @RequestParam("imagen") MultipartFile imagen) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        Marca marcaObj = objectMapper.readValue(marca, Marca.class);

        Marca marca1 = service.nuevo(marcaObj, imagen);
        return new ResponseEntity<Marca>(marca1, HttpStatus.CREATED);
    }
    //ACTUALIZAR MARCA
    @PutMapping("/marca")
    public ResponseEntity<Marca> actualizarMarca(
            @RequestParam("marca") String marca,
            @RequestParam("imagen") MultipartFile imagen,
            @PathVariable Integer id_marca) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Marca marcaObj = objectMapper.readValue(marca, Marca.class);
        Marca marca1 = service.actualizar(marcaObj, imagen);
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


    //eliminar datos de la marca
    @DeleteMapping("/marca/{id_marca}")
    public ResponseEntity<Void> eliminarMarca(@RequestBody @PathVariable("id_marca") Integer id_marca)throws Exception{
       Marca marca2 = service.listaPorId(id_marca);
       if (marca2 == null){
           throw new Exception("No existe id");
       }
       service.eliminar(id_marca);
       return new ResponseEntity<Void>(HttpStatus.OK);
    }
    //fotos-imagenes
    //imagenes
    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        System.out.println(fileName);
        // Obtener la imagen del archivo
        Path imagePath = Paths.get("src/main/resources/imagenes/" + fileName);
        File imageFile = imagePath.toFile();
        System.out.println(imageFile);

        // Verificar si el archivo existe y es accesible
        if (Files.exists(imagePath) && Files.isReadable(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);

            // Obtener el tipo de contenido según el formato de la imagen
            String contentType = FileTypeMap.getDefaultFileTypeMap().getContentType(imageFile);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
