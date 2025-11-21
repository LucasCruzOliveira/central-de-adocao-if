package github.iocentral_de_adocao_if.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String vinculoIFPB;  //Ex:aluno, Aluno, Servidor, Visitante, Professor

    public Usuario(String nome, String email, String senha, String telefone, String vinculoIFPB) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.vinculoIFPB = vinculoIFPB;
    }

}
