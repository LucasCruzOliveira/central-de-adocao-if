package github.iocentral_de_adocao_if.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private String sexo;

    @Column(length = 500) // Aumentei um pouco caso a descrição seja longa
    private String descricao;

    // --- MUDANÇA AQUI: De String para List ---
    @ElementCollection
    @CollectionTable(name = "animal_fotos", joinColumns = @JoinColumn(name = "animal_id"))
    @Column(name = "foto_url")
    private List<String> fotoUrls = new ArrayList<>();
    // -----------------------------------------

    private boolean adotado = false;

    // Construtor atualizado
    public Animal(String nome, String especie, String raca, int idade, String sexo, String descricao, List<String> fotoUrls) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.descricao = descricao;
        this.fotoUrls = fotoUrls;
    }
}