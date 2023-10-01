package com.optimedica.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface ImagenService {

    String cargarImagen(MultipartFile imagen, String categoria, String fecha);
    void eliminarImagen(String nombreImg);
}
