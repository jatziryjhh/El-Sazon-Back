package mx.edu.utez.El_Sazon_Back.model.producto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long id;
    @Column(length = 50, nullable = false)
    private String nombre_producto;
    @Column(length = 50, nullable = false)
    private String descripcion;
    @Column(length = 50, nullable = false)
    private Double precio;
    @Column(length = 50, nullable = false)
    private Integer cantidad_disponible;

    @JsonIgnore
    @ManyToMany(mappedBy = "productos")
    private List<Pedido> pedidos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL)
    private Inventario inventario;

    public Producto(Long id, String nombre_producto, String descripcion, Double precio, Integer cantidad_disponible, Categoria categoria) {
        this.id = id;
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad_disponible = cantidad_disponible;
        this.categoria = categoria;
    }
}
