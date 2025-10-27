package github.iocentral_de_adocao_if.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private String sexo;

    @Column(length = 100)
    private String descricao;

    private String fotoUrl; //caminho da imagem
    private boolean adotado = false; //verificação se já foi adotado


}
