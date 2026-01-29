package github.iocentral_de_adocao_if.demo.config;

import github.iocentral_de_adocao_if.demo.model.Role;
import github.iocentral_de_adocao_if.demo.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<Integer, String> rolesParaCriar = Map.of(
                0, "ROLE_ADMIN",
                1, "ALUNO",
                2, "PROFESSOR",
                3, "FUNCIONARIO",
                4, "VISITANTE"
        );

        rolesParaCriar.forEach((id, nome) -> {
            if (!roleRepository.existsById(Long.valueOf(id))) {
                roleRepository.save(new Role(id, nome));
                System.out.println("Role " + nome + " (ID: " + id + ") criada com sucesso!");
            }
        });
    }
}
