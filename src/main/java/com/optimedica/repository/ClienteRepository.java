package com.optimedica.repository;

import com.optimedica.model.Categoria;
import com.optimedica.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
