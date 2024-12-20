package mx.edu.utez.El_Sazon_Back.model.pedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.categoria.Categoria;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;
import mx.edu.utez.El_Sazon_Back.model.venta.Venta;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pedido")

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha_pedido;
    @Column(length = 50, nullable = false)
    private Double total_pedido;
    @Column(length = 50, nullable = false)
    private String status;


    @JsonIgnoreProperties({"pedido"})
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Venta venta;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pedido_producto", joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Pedido(Long id_pedido, LocalDateTime fecha_pedido, Double total_pedido, String status, Usuario usuario) {
        this.id_pedido = id_pedido;
        this.fecha_pedido = fecha_pedido;
        this.total_pedido = total_pedido;
        this.status= status;
        this.usuario = usuario;
    }
}
