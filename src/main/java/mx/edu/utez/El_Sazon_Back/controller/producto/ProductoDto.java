package mx.edu.utez.El_Sazon_Back.controller.producto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.categoria.Categoria;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;

@NoArgsConstructor
@Getter
@Setter

public class ProductoDto {
    private Long id;
    private String nombre_producto;
    private String descripcion;
    private Double precio;
    private Integer cantidad_disponible;
    private Categoria categoria;

    public Producto toEntity(){
        return new Producto(id, nombre_producto, descripcion, precio,
                cantidad_disponible, categoria);
    }
}
