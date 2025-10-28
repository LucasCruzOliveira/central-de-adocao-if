package github.iocentral_de_adocao_if.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Animal animal;

    private LocalDate dataAdocao=LocalDate.now();

}
