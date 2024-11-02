package mx.edu.utez.El_Sazon_Back.controller.inventario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.inventario.Inventario;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

public class InventarioDto {
    private Long id_inventario;
    private Integer cantidad_inventario;
    private LocalDateTime fecha_actualizacion;
    private Producto producto;

    public Inventario toEntity(){
        return new Inventario(id_inventario, cantidad_inventario,fecha_actualizacion, producto);
    }

}
