package mx.edu.utez.El_Sazon_Back.controller.pedido;

import jakarta.validation.Valid;
import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.pedido.PedidoRepository;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;
import mx.edu.utez.El_Sazon_Back.model.producto.ProductoRepository;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;
import mx.edu.utez.El_Sazon_Back.model.usuario.UsuarioRepository;
import mx.edu.utez.El_Sazon_Back.service.pedido.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/pedido")

public class PedidoController {

    private final PedidoService pedidoService;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoService pedidoService, ProductoRepository productoRepository, UsuarioRepository usuarioRepository, PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return pedidoService.getAll();
    }

    @PostMapping("/")
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody PedidoDto pedidoDto) {
        // Obtener el usuario a partir de usuarioId
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(pedidoDto.getUsuarioId());
        if (optionalUsuario.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Usuario no encontrado"), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = optionalUsuario.get();

        // Obtener los productos a partir de los productosIds
        List<Producto> productos = pedidoDto.getProductosIds().stream()
                .map(productoRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // Convertir el DTO en la entidad Pedido
        Pedido pedido = pedidoDto.toEntity(productos, usuario);

        // Guardar el pedido
        pedidoRepository.saveAndFlush(pedido);

        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED, false, "Pedido creado correctamente"), HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        return pedidoService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        return pedidoService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody PedidoDto pedidoDto) {
        return pedidoService.update(id, pedidoDto);
    }

}
