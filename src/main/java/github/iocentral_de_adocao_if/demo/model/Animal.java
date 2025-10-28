package github.iocentral_de_adocao_if.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private String sexo;

    @Column(length = 100)
    private String descricao;

    private String fotoUrl; //caminho da imagem
    private boolean adotado = false; //verificação se já foi adotado

    public Animal(boolean adotado, String fotoUrl, String descricao, String sexo, int idade, String raca, String especie, String nome) {
        this.adotado = adotado;
        this.fotoUrl = fotoUrl;
        this.descricao = descricao;
        this.sexo = sexo;
        this.idade = idade;
        this.raca = raca;
        this.especie = especie;
        this.nome = nome;
    }
}
