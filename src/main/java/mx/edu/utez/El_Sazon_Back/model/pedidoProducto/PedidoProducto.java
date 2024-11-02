package mx.edu.utez.El_Sazon_Back.model.pedidoProducto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.categoria.Categoria;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pedido_producto")

public class PedidoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedidoproducto;
    //es la cantidad de los productos compradossss
    @Column(length = 50, nullable = false)
    private Integer cantidad_productos_pedidos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public PedidoProducto(Long id_pedidoproducto, Integer cantidad_productos_pedidos, Pedido pedido, Producto producto) {
        this.id_pedidoproducto = id_pedidoproducto;
        this.cantidad_productos_pedidos = cantidad_productos_pedidos;
        this.pedido = pedido;
        this.producto = producto;
    }
}
