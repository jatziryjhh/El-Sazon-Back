package mx.edu.utez.El_Sazon_Back.model.venta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByFechaventa(LocalDateTime fechaventa);
}
