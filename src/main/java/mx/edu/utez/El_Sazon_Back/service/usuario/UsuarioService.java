package mx.edu.utez.El_Sazon_Back.service.usuario;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;
import mx.edu.utez.El_Sazon_Back.model.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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

        usuario = usuarioRepository.saveAndFlush(usuario);
        return new ResponseEntity<>(new ApiResponse(usuario, HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        Optional<Usuario> findById = usuarioRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Id no encontrado"),
                   HttpStatus.BAD_REQUEST);
        usuarioRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Usuario elimiando correctamente"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Usuario> findById = usuarioRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Id no encontrado"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(usuarioRepository.findById(id), HttpStatus.OK), HttpStatus.OK);
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
