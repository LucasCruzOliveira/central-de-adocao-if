package github.iocentral_de_adocao_if.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String VinculoIFPB;  //Ex:aluno, Aluno, Servidor, Visitante, Professor



}
