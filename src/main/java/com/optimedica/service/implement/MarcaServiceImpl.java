package com.optimedica.service.implement;

import com.optimedica.model.Marca;
import com.optimedica.repository.MarcaRepository;
import com.optimedica.service.MarcaService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {
    @Autowired
    private MarcaRepository repository;
    @Override
    @Transactional
    public List<Marca> listarMarca() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Marca listaPorId(Integer id_marca) {
        return repository.findById(id_marca).orElse(null);
    }

    @Override
    @Transactional
    public Marca nuevo(Marca marca) {
        return repository.save(marca);
    }

    @Override
    public Marca actualizar(Marca marca) {
        return repository.save(marca);
    }

    @Override
    @Transactional
    public void eliminar(Integer id_marca) {
        repository.deleteById(id_marca);
    }
}
