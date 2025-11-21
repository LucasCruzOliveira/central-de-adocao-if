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

    public Animal(UUID id, String nome, String especie, String raca, int idade, String sexo, String descricao, String fotoUrl, boolean adotado) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.descricao = descricao;
        this.fotoUrl = fotoUrl;
        this.adotado = adotado;
    }

    public Animal(String nome, String especie, String raca, int idade, String sexo, String descricao, String fotoUrl, boolean adotado) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.descricao = descricao;
        this.fotoUrl = fotoUrl;
        this.adotado = adotado;
    }

    public Animal(String nome, String especie, String raca, int idade, String sexo, String descricao, String fotoUrl) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.descricao = descricao;
        this.fotoUrl = fotoUrl;
    }
}

