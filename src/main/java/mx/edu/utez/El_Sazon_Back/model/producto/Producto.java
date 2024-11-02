package mx.edu.utez.El_Sazon_Back.model.producto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "producto")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;
    @Column(length = 50, nullable = false)
    private String nombre_prodcuto;
    @Column(length = 50, nullable = false)
    private String descripcion;
    @Column(length = 50, nullable = false)
    private Double precio;
    @Column(length = 50, nullable = false)
    private Integer cantidad_disponible;
}
