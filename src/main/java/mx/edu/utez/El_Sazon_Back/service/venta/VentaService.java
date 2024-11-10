package mx.edu.utez.El_Sazon_Back.service.venta;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.controller.venta.VentaDto;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.pedido.PedidoRepository;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;
import mx.edu.utez.El_Sazon_Back.model.usuario.UsuarioRepository;
import mx.edu.utez.El_Sazon_Back.model.venta.Venta;
import mx.edu.utez.El_Sazon_Back.model.venta.VentaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@Transactional

public class VentaService {
    private final VentaRepository ventaRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public VentaService(VentaRepository ventaRepository, PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository) {
        this.ventaRepository = ventaRepository;
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(ventaRepository.findAll(),
                HttpStatus.OK), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> register(VentaDto ventaDto) {
        try {
            Venta venta = ventaDto.toEntity();

            // Buscar el usuario y pedido por el id
            Usuario usuario = usuarioRepository.findById(ventaDto.getUsuario().getId_usuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            venta.setUsuario(usuario); // Asignar usuario a la venta

            Pedido pedido = pedidoRepository.findById(ventaDto.getPedido().getId_pedido())
                    .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
            venta.setPedido(pedido);

            ventaRepository.save(venta);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Venta registrada"), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al registrar la venta: " + e.getMessage());
            System.out.println("Los ids son " + ventaDto.getUsuario().getId_usuario() + " " + ventaDto.getPedido().getId_pedido());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error al registrar la venta"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Venta> findById = ventaRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el Id"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(ventaRepository.findById(id),HttpStatus.OK), HttpStatus.OK);
    }
}
