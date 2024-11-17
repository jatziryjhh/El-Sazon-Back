package mx.edu.utez.El_Sazon_Back.service.producto;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.categoria.Categoria;
import mx.edu.utez.El_Sazon_Back.model.categoria.CategoriaRepository;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;
import mx.edu.utez.El_Sazon_Back.model.producto.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional

public class ProductoService {
    private  final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(productoRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(Producto producto){
        producto = productoRepository.saveAndFlush(producto);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Producto registrado"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Producto> findById = productoRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el producto"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(productoRepository.findById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(Long id) {
        // Busca el producto por su ID
        Optional<Producto> productoOptional = productoRepository.findById(id);

        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();

            // Desvincula el producto de la categoría antes de eliminarlo
            if (producto.getCategoria() != null) {
                Categoria categoria = producto.getCategoria();
                // Remueve el producto de la lista de productos de la categoría
                categoria.getProductos().remove(producto);
                producto.setCategoria(null); // Desvincula el producto de la categoría
                categoriaRepository.save(categoria); // Guarda los cambios en la categoría
            }

            // Elimina el producto
            productoRepository.deleteById(id);

            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Producto eliminado correctamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Id no encontrado"), HttpStatus.NOT_FOUND);
        }
    }


    @Transactional(rollbackFor = {SQLException.class})
    public  ResponseEntity<ApiResponse> update(Long id, Producto updateProducto){
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isPresent()){
            Producto producto = optionalProducto.get();
            producto.setNombre_producto(updateProducto.getNombre_producto());
            producto.setDescripcion(updateProducto.getDescripcion());
            producto.setCantidad_disponible(updateProducto.getCantidad_disponible());
            producto.setPrecio(updateProducto.getPrecio());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Producto Actualizado"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el producto"), HttpStatus.NOT_FOUND);
        }
    }
}
