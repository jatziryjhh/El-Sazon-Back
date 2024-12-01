package mx.edu.utez.El_Sazon_Back.service.pedido;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.controller.pedido.PedidoDto;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.pedido.PedidoRepository;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;
import mx.edu.utez.El_Sazon_Back.model.producto.ProductoRepository;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;
import mx.edu.utez.El_Sazon_Back.model.usuario.UsuarioRepository;
import mx.edu.utez.El_Sazon_Back.model.venta.Venta;
import mx.edu.utez.El_Sazon_Back.model.venta.VentaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional

public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository, VentaRepository ventaRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(pedidoRepository.findAll(),
                HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(Pedido pedido){
        pedido = pedidoRepository.saveAndFlush(pedido);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Pedido registrado"), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true,
                    "No se encontró el pedido"), HttpStatus.NOT_FOUND);
        }

        Pedido pedido = pedidoOptional.get();

        // Aqui desvicunle el pedido de la relación con la venta
        if (pedido.getVenta() != null) {
            Venta venta = pedido.getVenta();
            venta.setPedido(null);
            ventaRepository.save(venta);
            pedido.setVenta(null);
        }

        // Desvincule los productos asociados
        if (pedido.getProductos() != null && !pedido.getProductos().isEmpty()) {
            pedido.getProductos().clear();
            pedidoRepository.save(pedido);
        }
        pedidoRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Pedido eliminado correctamente"), HttpStatus.OK);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Pedido> findById = pedidoRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el Id"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(pedidoRepository.findById(id),HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> update(Long id, PedidoDto pedidoDto) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);

        if (optionalPedido.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el pedido"), HttpStatus.NOT_FOUND);
        }

        // Obtener el pedido encontrado
        Pedido pedido = optionalPedido.get();

        // Actualizar los valores del pedido
        pedido.setFecha_pedido(pedidoDto.getFecha_pedido());
        pedido.setTotal_pedido(pedidoDto.getTotal_pedido());
        pedido.setStatus(pedidoDto.getStatus());

        // Obtener el usuario usando el ID del DTO
        Usuario usuario = usuarioRepository.findById(pedidoDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        pedido.setUsuario(usuario);  // Establecer el usuario actualizado

        // Actualizar los productos asociados
        if (pedidoDto.getProductosIds() != null) {
            List<Producto> productos = pedidoDto.getProductosIds().stream()
                    .map(productoRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            pedido.setProductos(productos);  // Actualizar los productos en el pedido
        }

        // Guardar y actualizar el pedido en la base de datos
        pedidoRepository.saveAndFlush(pedido);

        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Pedido actualizado correctamente"), HttpStatus.OK);
    }

}
