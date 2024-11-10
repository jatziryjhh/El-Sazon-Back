package mx.edu.utez.El_Sazon_Back.controller.venta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;
import mx.edu.utez.El_Sazon_Back.model.venta.Venta;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

public class VentaDto {
    private Long id_venta;
    private LocalDateTime fechaventa;
    private Double total_venta;
    private Pedido pedido;
    private Usuario usuario;

    public Venta toEntity(){
        return new Venta(id_venta, fechaventa, total_venta, pedido, usuario);
    }
}
