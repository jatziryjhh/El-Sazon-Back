package mx.edu.utez.El_Sazon_Back.service.inventario;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.controller.inventario.InventarioDto;
import mx.edu.utez.El_Sazon_Back.controller.producto.ProductoDto;
import mx.edu.utez.El_Sazon_Back.controller.venta.VentaDto;
import mx.edu.utez.El_Sazon_Back.model.inventario.Inventario;
import mx.edu.utez.El_Sazon_Back.model.inventario.InventarioRepository;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;
import mx.edu.utez.El_Sazon_Back.model.producto.ProductoRepository;
import mx.edu.utez.El_Sazon_Back.model.venta.Venta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional

public class InventarioService {
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public InventarioService(InventarioRepository inventarioRepository, ProductoRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(inventarioRepository.findAll(),
                HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> registerAll(Inventario inventario){
        inventario = inventarioRepository.saveAndFlush(inventario);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Registro de producto e inventario exitoso"), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> registerProduct(InventarioDto inventarioDto) {
        try {
            Inventario inventario = inventarioDto.toEntity();

            // Primero buscar id del producto
            Producto producto = productoRepository.findById(inventarioDto.getProducto().getId_producto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            inventario.setProducto(producto); // Asignar produtcto al inventario

            inventarioRepository.save(inventario);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Producto registrado"), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al registrar la venta: " + e.getMessage());
            System.out.println("Los ids son " + inventarioDto.getProducto().getId_producto());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error al registrar la venta"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Inventario> findById = inventarioRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el Id"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(inventarioRepository.findById(id),HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public  ResponseEntity<ApiResponse> update(Long id, Inventario updateInventario){
        Optional<Inventario> optionalInventario = inventarioRepository.findById(id);
        if (optionalInventario.isPresent()){
            Inventario inventario = optionalInventario.get();
            inventario.setCantidad_inventario(updateInventario.getCantidad_inventario());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Cantidad de productos actualizada"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el Id"), HttpStatus.NOT_FOUND);
        }
    }
}
