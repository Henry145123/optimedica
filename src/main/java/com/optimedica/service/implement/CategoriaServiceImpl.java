package com.optimedica.service.implement;

import com.optimedica.model.Categoria;
import com.optimedica.repository.CategoriaRepository;
import com.optimedica.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;
    @Override
    public List<Categoria> listarCat() {
        return repository.findAll();
    }

    @Override
    public Categoria listarPorId(Integer id_categoria) {
        return repository.findById(id_categoria).orElse(null);
    }

    @Override
    public Categoria nuevoCat(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public Categoria actualizarCat(Categoria categoria) {
        return repository.saveAndFlush(categoria);
    }

    @Override
    public void eliminarCat(Integer id_categoria) {
        repository.deleteById(id_categoria);
    }
}
