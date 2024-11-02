package mx.edu.utez.El_Sazon_Back.model.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "usuario")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @Column(length = 50, nullable = false)
    private String nombre;
    @Column(length = 50, nullable = false)
    private String apellidom;
    @Column(length = 50, nullable = true)
    private String apellidop;
    @Column(length = 50, nullable = false)
    private String correo;
    @Column(length = 50, nullable = false)
    private String contrasena;
}
