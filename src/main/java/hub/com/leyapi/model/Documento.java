package hub.com.leyapi.model;

import hub.com.leyapi.nums.DocumentoEstado;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Table(name = "documento")
@Entity
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumento;

    @Column(nullable = false, length = 50)
    private String titulo;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String archivoUrl;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DocumentoEstado estado;

    @Column(nullable = false, length = 50)
    private String numeroExpediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;
}
