package com.optimedica.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileMarcaService {
    public String cargar(MultipartFile nombreFoto, String marca, String fecha);

    public void eliminar(String nombreFoto);
}

