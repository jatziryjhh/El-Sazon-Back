package mx.edu.utez.El_Sazon_Back.model.usuario;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;
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
    @Column(name = "id_usuario") // Esto asegura que JPA use "id_usuario" en la BD
    private Long id;
    @Column(length = 50, nullable = false)
    private String nombre;
    @Column(length = 50, nullable = false)
    private String apellidop;
    @Column(length = 50, nullable = true)
    private String apellidom;
    @Column(length = 50, nullable = false)
    private String correo;
    @Column(length = 150, nullable = false)
    private String contrasena;
    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean status;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    private List<Pedido> pedidos;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    private List<Venta> ventas;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;


    public Usuario(Long id, String nombre, String apellidop, String apellidom, String correo, String contrasena, Boolean status) {
        this.id = id;
        this.nombre = nombre;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.correo = correo;
        this.contrasena = contrasena;
        this.status = status;
    }

    public Usuario(Long id, String nombre, String apellidop, String apellidom, String correo, String contrasena, Boolean status, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.correo = correo;
        this.contrasena = contrasena;
        this.status = status;
        this.rol = rol;
    }

    public Usuario(String correo, String contrasena, Rol rol) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Usuario(String nombre, String apellidop, String apellidom, String correo, String contrasena, Boolean status) {
        this.nombre = nombre;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.correo = correo;
        this.contrasena = contrasena;
        this.status = status;
    }
}

