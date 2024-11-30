package mx.edu.utez.El_Sazon_Back.config;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.El_Sazon_Back.model.categoria.Categoria;
import mx.edu.utez.El_Sazon_Back.model.categoria.CategoriaRepository;
import mx.edu.utez.El_Sazon_Back.model.rol.Rol;
import mx.edu.utez.El_Sazon_Back.model.rol.RolRepository;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;
import mx.edu.utez.El_Sazon_Back.model.usuario.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor

public class InitialConfig implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void run(String... args) throws Exception {
        Rol adminRole = getOrSaveRol(new Rol("Gerente"));
        getOrSaveRol(new Rol("Empleado"));
        getOrSaveRol(new Rol("Cliente"));

        Usuario user = getOrSaveUser(
                new Usuario("Lizbeth", "Santibañez", "Cruz", "lizz@gmail.com", encoder.encode("michifus"), true)
        );
        saveUserRol(user.getId(), (long) adminRole.getId_role());

        createCategoria("Alimentos");
        createCategoria("Postres");
        createCategoria("Bebidas");
    }


    @Transactional
    public Rol getOrSaveRol(Rol rol) {
        Optional<Rol> foundRol = rolRepository.findByNombre(rol.getNombre());
        return foundRol.orElseGet(() -> {
            rolRepository.saveAndFlush(rol);
            return rolRepository.findByNombre(rol.getNombre()).orElse(null);
        });
    }

    @Transactional
    public Usuario getOrSaveUser(Usuario user){
        Optional<Usuario> foundUser = usuarioRepository.findByCorreo(user.getCorreo());
        return foundUser.orElseGet(() -> {
            Rol userRol = user.getRol();
            if (userRol != null){
                if (userRol.getId_role() == 0){
                    userRol = getOrSaveRol(userRol);
                }
            }
            user.setRol(userRol);
            return usuarioRepository.saveAndFlush(user);
        });
    }

    @Transactional
    public void saveUserRol(Long userId, Long roleId){
        Usuario usuario = usuarioRepository.findById(userId).orElse(null);
        if (usuario != null){
            Rol newRol = rolRepository.findById(roleId).orElse(null);
            if (newRol != null){
                usuario.setRol(newRol);
                usuarioRepository.save(usuario);
            }
        }
    }

    @Transactional
    public void createCategoria(String nombreCategoria){
        Optional<Categoria> foundCategoria = categoriaRepository.findByNombrecategoria(nombreCategoria);
        if (foundCategoria.isEmpty()){
            Categoria categoria = new Categoria();
            categoria.setNombrecategoria(nombreCategoria);
            categoriaRepository.saveAndFlush(categoria);
        }
    }
}
