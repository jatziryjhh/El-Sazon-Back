package mx.edu.utez.El_Sazon_Back.model.inventario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table

public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_inventario;
    @Column(length = 50, nullable = false)
    private Integer cantidad_inventario;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha_actualizacion;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "prodcuto_id")
    private Producto producto;

    public Inventario(Long id_inventario, Integer cantidad_inventario, LocalDateTime fecha_actualizacion, Producto producto) {
        this.id_inventario = id_inventario;
        this.cantidad_inventario = cantidad_inventario;
        this.fecha_actualizacion = fecha_actualizacion;
        this.producto = producto;
    }
}
