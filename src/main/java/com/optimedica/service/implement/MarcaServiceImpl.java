package com.optimedica.service.implement;

import com.optimedica.model.Marca;
import com.optimedica.model.Producto;
import com.optimedica.repository.MarcaRepository;
import com.optimedica.service.FileMarcaService;
import com.optimedica.service.MarcaService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {
    @Autowired
    private MarcaRepository repository;

    @Autowired
    private FileMarcaService fileMarcaService;
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
    public Marca nuevo(Marca marca, MultipartFile file) {
        Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
        marca.setFecha(fechaActual);

        Date fecha = new Date(marca.getFecha().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = dateFormat.format(fecha);
        String nombreFile = fileMarcaService.cargar(file, marca.getNombre(),fechaFormateada);
        marca.setImagen(nombreFile);

        Marca marca1 = repository.save(marca);
        return  marca1;
    }

    @Override
    public Marca actualizar(Marca marca, MultipartFile file) {

        Marca marcaAntiguo = listaPorId(marca.getId_marca());

        if(marcaAntiguo.getImagen() != marca.getImagen()){
            System.out.println("imagenProd"+ marcaAntiguo.getImagen());
            fileMarcaService.eliminar(marcaAntiguo.getImagen());
            // Convertir Timestamp a Date
            Date fecha = new Date(marca.getFecha().getTime());

            // Formatear la fecha como una cadena
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaFormateada = dateFormat.format(fecha);

            String nombreImagen = fileMarcaService.cargar(file,marca.getNombre(),fechaFormateada);
            marca.setImagen(nombreImagen);
        }

        Marca marca1 = repository.save(marca);

        return marca1;
    }

    @Override
    @Transactional
    public void eliminar(Integer id_marca) {
        repository.deleteById(id_marca);
    }

    @Override
    public Page<Marca> ListarPaginas(Pageable pageable) {
            return repository.findAll(pageable);
    }
}
