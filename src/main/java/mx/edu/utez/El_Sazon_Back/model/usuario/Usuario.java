package mx.edu.utez.El_Sazon_Back.model.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.rol.Rol;
import mx.edu.utez.El_Sazon_Back.model.venta.Venta;

import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    private List<Pedido> pedidos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    private List<Venta> ventas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public Usuario(Long id_usuario, String nombre, String apellidom, String apellidop, String correo, String contrasena) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellidom = apellidom;
        this.apellidop = apellidop;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Usuario(Long id_usuario, String nombre, String apellidom, String apellidop, String correo, String contrasena, Rol rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellidom = apellidom;
        this.apellidop = apellidop;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }
}

