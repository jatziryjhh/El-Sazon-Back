package mx.edu.utez.El_Sazon_Back.model.rol;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
