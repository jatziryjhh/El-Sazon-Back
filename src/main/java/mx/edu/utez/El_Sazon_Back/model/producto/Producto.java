package mx.edu.utez.El_Sazon_Back.model.producto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.categoria.Categoria;
import mx.edu.utez.El_Sazon_Back.model.inventario.Inventario;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.pedidoProducto.PedidoProducto;

import java.util.List;

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
    private String nombre_producto;
    @Column(length = 50, nullable = false)
    private String descripcion;
    @Column(length = 50, nullable = false)
    private Double precio;
    @Column(length = 50, nullable = false)
    private Integer cantidad_disponible;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "producto")
    private Inventario inventario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "producto")
    private List<PedidoProducto> pedidoProductos;



}
