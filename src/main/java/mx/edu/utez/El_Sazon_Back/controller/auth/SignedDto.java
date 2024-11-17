package mx.edu.utez.El_Sazon_Back.controller.auth;

import lombok.Value;
import mx.edu.utez.El_Sazon_Back.model.rol.Rol;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;

@Value
public class SignedDto {
    String token;
    String tokenType;
    Usuario usuario;
    Rol rol;
}
