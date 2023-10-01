package com.optimedica.service.implement;

import com.optimedica.service.ImagenService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ImagenServiceImpl implements ImagenService {


    @Override
    public String cargarImagen(MultipartFile imagen, String categoria, String fecha) {
        String nombreImg = " ";

        try {
            categoria = categoria.replaceAll("[^a-zA-Z0-9_-]", "_");
            fecha = fecha.replaceAll("[^a-zA-Z0-9_-]", "_");
            nombreImg = categoria+"_"+fecha+"_"+imagen.getOriginalFilename();
            Path targetPath = Path.of("src/main/resources/imagenes/", nombreImg);

            Files.copy(imagen.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return nombreImg;

        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + imagen.getOriginalFilename() + ". ¡Inténtalo de nuevo!",ex);
        }

    }

    @Override
    public void eliminarImagen(String nombreImg) {
        Path filePath = Path.of("src/main/resources/imagenes/", nombreImg);

        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
