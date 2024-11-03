package mx.edu.utez.El_Sazon_Back.controller.pedido;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

public class PedidoDto {
    private Long id_pedido;
    private LocalDateTime fecha_pedido;
    private Double total_pedido;
    private String status;
    private Usuario usuario;

    public Pedido toEntity(){
        return new Pedido(id_pedido, fecha_pedido, total_pedido, status, usuario);
    }
}
