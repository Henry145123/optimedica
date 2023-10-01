package com.optimedica.service.implement;

import com.optimedica.model.Producto;
import com.optimedica.repository.ProductoRepository;
import com.optimedica.service.ImagenService;
import com.optimedica.service.ProductoService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository repository;

    @Autowired
    private ImagenService imagenService;
    @Override
    @Transactional
    public List<Producto> listarProducto() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Producto listaPorId(Integer id_producto) {
        return repository.findById(id_producto).orElse(null);
    }

    @Override
    @Transactional
    public Producto nuevo(Producto producto, MultipartFile imagen) {
        // Crear un objeto Timestamp con la fecha actual
        Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
        producto.setFecha(fechaActual);

        // Convertir Timestamp a Date
        Date fecha = new Date(producto.getFecha().getTime());

        // Formatear la fecha como una cadena
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = dateFormat.format(fecha);

        String nombreImagen = imagenService.cargarImagen(imagen,producto.getCategoria().getNombre(),fechaFormateada);
        producto.setImagen(nombreImagen);
        Producto prod = repository.save(producto);

        return prod;
    }

    @Override
    public Producto actualizar(Producto producto, MultipartFile imagen) {
        Producto productoAntiguo = listaPorId(producto.getId_producto());

        if(productoAntiguo.getImagen() != producto.getImagen()){
            System.out.println("imagenProd"+ productoAntiguo.getImagen());
            imagenService.eliminarImagen(productoAntiguo.getImagen());
            // Convertir Timestamp a Date
            Date fecha = new Date(producto.getFecha().getTime());

            // Formatear la fecha como una cadena
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaFormateada = dateFormat.format(fecha);

            String nombreImagen = imagenService.cargarImagen(imagen,producto.getCategoria().getNombre(),fechaFormateada);
            producto.setImagen(nombreImagen);
        }

        Producto prod = repository.save(producto);

        return prod;
    }

    @Override
    @Transactional
    public void eliminar(Integer id_producto) {
       Producto productoEliminado = listaPorId(id_producto);
       if(productoEliminado!=null){
           repository.deleteById(id_producto);
           imagenService.eliminarImagen(productoEliminado.getImagen());
       }

    }
}
