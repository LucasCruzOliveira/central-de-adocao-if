package github.iocentral_de_adocao_if.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "roles")
public class Role {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nome;

}
