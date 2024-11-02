package mx.edu.utez.El_Sazon_Back.model.rol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rol")

public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;
    @Column(length = 50, nullable = false)
    private String nombre_rol;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "rol" )
    private List<Usuario> usuarios;
}
