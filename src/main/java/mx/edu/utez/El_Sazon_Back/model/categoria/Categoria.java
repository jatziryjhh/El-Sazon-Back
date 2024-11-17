package mx.edu.utez.El_Sazon_Back.model.categoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.El_Sazon_Back.model.producto.Producto;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "categoria")

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_categoria;
    @Column(length = 50, nullable = false)
    private String nombre_categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Producto> productos;


    public Categoria(Long idCategoria, String nombreCategoria, List<Producto> productos) {
    }
}
