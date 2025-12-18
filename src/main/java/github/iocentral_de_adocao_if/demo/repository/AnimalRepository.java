package github.iocentral_de_adocao_if.demo.repository;

import github.iocentral_de_adocao_if.demo.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {}

