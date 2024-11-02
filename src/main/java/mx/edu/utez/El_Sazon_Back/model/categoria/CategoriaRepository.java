package mx.edu.utez.El_Sazon_Back.model.categoria;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    Optional<Categoria> findByNombre_categoria(String nombre_categoria);
}
