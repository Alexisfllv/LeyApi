package hub.com.leyapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chives")
public class Chives {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChives;

    @Column(nullable = false, length = 200)
    private String nombre;   // nombre original

    @Column(nullable = false, length = 200)
    private String url;      // ruta en el servidor

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idActa", nullable = false)
    private Actas acta;
}
