package mx.edu.utez.El_Sazon_Back.model.producto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre_producto(String nombre_producto);

}
