    package mx.edu.utez.El_Sazon_Back.model.rol;

    import com.fasterxml.jackson.annotation.JsonCreator;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import mx.edu.utez.El_Sazon_Back.model.usuario.Usuario;

    import java.util.List;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @Table(name = "rol")

    public class Rol {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id_role;
        @Column(length = 50, nullable = false)
        private String nombre;


        @JsonIgnoreProperties({"rol"})
        @JsonIgnore
        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "rol" )
        private List<Usuario> usuarios;

        public Rol(String nombre) {
            this.nombre = nombre;
        }

        public Rol(int id_role, String nombre) {
            this.id_role = id_role;
            this.nombre = nombre;
        }
    }
