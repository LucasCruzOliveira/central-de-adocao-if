package github.iocentral_de_adocao_if.demo.repository;

import github.iocentral_de_adocao_if.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(int id);
}
