package mx.edu.utez.El_Sazon_Back.controller.rol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.rol.Rol;

@NoArgsConstructor
@Getter
@Setter

public class RolDto {
    private Long id_rol;
    private String nombre;

}
