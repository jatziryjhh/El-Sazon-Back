package mx.edu.utez.El_Sazon_Back.model.pedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.pedidoProducto.PedidoProducto;
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Venta venta;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pedido")
    private List<PedidoProducto> pedidoProductos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
