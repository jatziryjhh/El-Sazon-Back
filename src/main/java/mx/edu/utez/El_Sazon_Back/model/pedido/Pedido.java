package mx.edu.utez.El_Sazon_Back.model.pedido;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pedido")

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha_pedido;
    @Column(length = 50, nullable = false)
    private Double total_pedido;
}
