package hub.com.leyapi.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "actas")
public class Actas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActa;

    private String titulo;

    @OneToMany(mappedBy = "acta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chives> chives = new ArrayList<>();
}
