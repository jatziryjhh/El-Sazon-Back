package mx.edu.utez.El_Sazon_Back.model.inventario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
