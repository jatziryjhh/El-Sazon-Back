package mx.edu.utez.El_Sazon_Back.service.usuario;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.pedido.PedidoRepository;
import mx.edu.utez.El_Sazon_Back.model.rol.Rol;
import mx.edu.utez.El_Sazon_Back.model.rol.RolRepository;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;
import mx.edu.utez.El_Sazon_Back.model.usuario.UsuarioRepository;
import mx.edu.utez.El_Sazon_Back.model.venta.Venta;
import mx.edu.utez.El_Sazon_Back.model.venta.VentaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PedidoRepository pedidoRepository;
    private final VentaRepository ventaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PedidoRepository pedidoRepository, VentaRepository ventaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.pedidoRepository = pedidoRepository;
        this.ventaRepository = ventaRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll() {
        return new ResponseEntity<>(new ApiResponse(usuarioRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(Usuario usuario) {
        Optional<Usuario> findUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (findUsuario.isPresent())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "El correo ya ha sido registrado"), HttpStatus.BAD_REQUEST);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setContrasena(encoder.encode(usuario.getContrasena()));
        usuario = usuarioRepository.saveAndFlush(usuario);
        return new ResponseEntity<>(new ApiResponse(usuario, HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Usuario> findById = usuarioRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Id no encontrado"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(usuarioRepository.findById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(Long id) {
        // Busca al usuario por su ID
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Desvincule el usuario de sus pedidos
            if (usuario.getPedidos() != null && !usuario.getPedidos().isEmpty()) {
                for (Pedido pedido : usuario.getPedidos()) {
                    pedido.setUsuario(null);
                    pedidoRepository.save(pedido);
                }
                usuario.getPedidos().clear();
            }

            // Desvincule el usuario de sus ventas
            if (usuario.getVentas() != null && !usuario.getVentas().isEmpty()) {
                for (Venta venta : usuario.getVentas()) {
                    venta.setUsuario(null);
                    ventaRepository.save(venta);
                }
                usuario.getVentas().clear();
            }

            // Desvincule al usuario de su rol
            if (usuario.getRol() != null) {
                Rol rol = usuario.getRol();
                rol.getUsuarios().remove(usuario);
                usuario.setRol(null);
                rolRepository.save(rol);
            }
            usuarioRepository.deleteById(id);

            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Usuario eliminado correctamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Id no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> update(Long id, Usuario updateUsuario){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setNombre(updateUsuario.getNombre());
            usuario.setApellidom(updateUsuario.getApellidom());
            usuario.setApellidop(updateUsuario.getApellidop());
            usuario.setCorreo(updateUsuario.getCorreo());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Usuario actualizado"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Usuario con ese id no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

}
