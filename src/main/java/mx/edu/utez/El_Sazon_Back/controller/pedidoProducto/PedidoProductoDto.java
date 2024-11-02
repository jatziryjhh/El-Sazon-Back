package mx.edu.utez.El_Sazon_Back.controller.pedidoProducto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.pedidoProducto.PedidoProducto;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;

@NoArgsConstructor
@Getter
@Setter

public class PedidoProductoDto {
    private Long id_pedidoproducto;
    private Integer cantidad_productos_pedidos;
    private Pedido pedido;
    private Producto producto;

    public PedidoProducto toEntity(){
        return new PedidoProducto(id_pedidoproducto, cantidad_productos_pedidos, pedido, producto);
    }
}
