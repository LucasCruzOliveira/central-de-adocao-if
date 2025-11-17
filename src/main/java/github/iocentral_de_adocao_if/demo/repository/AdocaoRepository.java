package github.iocentral_de_adocao_if.demo.repository;

import github.iocentral_de_adocao_if.demo.model.Adocao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AdocaoRepository extends JpaRepository<Adocao, UUID> {

}
