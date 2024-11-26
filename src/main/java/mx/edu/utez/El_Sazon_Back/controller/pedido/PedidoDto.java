package mx.edu.utez.El_Sazon_Back.controller.pedido;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

public class PedidoDto {
    private Long id_pedido;
    private LocalDateTime fecha_pedido;
    private Double total_pedido;
    private String status;
    private Long usuarioId;
    private List<Long> productosIds;

    public Pedido toEntity(List<Producto> productos, Usuario usuario) {
        Pedido pedido = new Pedido();
        pedido.setId_pedido(this.id_pedido);
        pedido.setFecha_pedido(this.fecha_pedido);
        pedido.setTotal_pedido(this.total_pedido);
        pedido.setStatus(this.status);
        pedido.setUsuario(usuario);
        pedido.setProductos(productos);
        return pedido;
    }
}
