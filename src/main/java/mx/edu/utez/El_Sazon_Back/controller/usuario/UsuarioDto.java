package mx.edu.utez.El_Sazon_Back.controller.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.rol.Rol;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;

@NoArgsConstructor
@Getter
@Setter

public class UsuarioDto {
    private Long id;
    private String nombre;
    private String apellidom;
    private String apellidop;
    private String correo;
    private String contrasena;
    private Rol rol;

    public Usuario toEntity(){
        if (rol == null)
            return new Usuario(id, nombre, apellidom, apellidop, correo, contrasena);
        return new Usuario(id, nombre, apellidom, apellidop, correo, contrasena, rol);
    }
}
