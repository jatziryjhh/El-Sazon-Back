package mx.edu.utez.El_Sazon_Back.model.venta;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "venta")

public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha_venta;
    @Column(length = 50, nullable = false)
    private Double total_venta;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
