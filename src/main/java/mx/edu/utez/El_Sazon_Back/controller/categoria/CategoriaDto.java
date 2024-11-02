package mx.edu.utez.El_Sazon_Back.controller.categoria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.categoria.Categoria;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter

public class CategoriaDto {
    private Long id_categoria;
    private String nombre_categoria;
    private List<Producto> productos;

    public Categoria toEntity(){
        return new Categoria(id_categoria, nombre_categoria, productos);
    }
}
