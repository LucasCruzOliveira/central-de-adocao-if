package github.iocentral_de_adocao_if.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data

public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Animal animal;

    private LocalDate dataAdocao=LocalDate.now();

}
