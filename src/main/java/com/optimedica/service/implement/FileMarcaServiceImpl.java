package com.optimedica.service.implement;

import com.optimedica.service.FileMarcaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileMarcaServiceImpl implements FileMarcaService {

    @Override
    public String cargar(MultipartFile nombreFoto, String marca, String fecha) {
        String nombreImg = " ";

        try {
            marca = marca.replaceAll("[^a-zA-Z0-9_-]", "_");
            fecha = fecha.replaceAll("[^a-zA-Z0-9_-]", "_");
            nombreImg = marca+"_"+fecha+"_"+nombreFoto.getOriginalFilename();
            Path targetPath = Path.of("src/main/resources/imagenes/", nombreImg);

            Files.copy(nombreFoto.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return nombreImg;

        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + nombreFoto.getOriginalFilename() + ". ¡Inténtalo de nuevo!",ex);
        }
    }

    @Override
    public void eliminar(String nombreFoto) {
        Path filePath = Path.of("src/main/resources/imagenes/", nombreFoto);

        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
