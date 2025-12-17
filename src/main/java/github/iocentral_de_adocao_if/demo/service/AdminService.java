package github.iocentral_de_adocao_if.demo.service;

import github.iocentral_de_adocao_if.demo.model.Admin;
import github.iocentral_de_adocao_if.demo.model.Role;
import github.iocentral_de_adocao_if.demo.repository.AdminRepository;
import github.iocentral_de_adocao_if.demo.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final AdminRepository repository;
    private final PasswordEncoder encoder;
    private RoleRepository roleRepository;

    public AdminService(AdminRepository repository, PasswordEncoder encoder,  RoleRepository roleRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public List<Admin> listarTodos() {
        return repository.findAll();
    }

    public Admin buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado"));
    }

    @Transactional
    public Admin salvar(Admin admin) {

        // só pode existir 1 admin
        if (repository.count() > 0) {
            throw new RuntimeException("Já existe um administrador no sistema.");

        }

        // email duplicado
        if (repository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Email já está em uso!");
        }

        Role roleAdmin = roleRepository.findById(0)
                .orElseGet(() -> {
                    Role novaRole = new Role();
                    novaRole.setId(0);
                    novaRole.setNome("ROLE_ADMIN");
                    return roleRepository.save(novaRole);
                });

        // role admin
        admin.getRoles().add(roleAdmin);


        // criptografar senha
        admin.setSenha(encoder.encode(admin.getSenha()));


        return repository.save(admin);
    }

    @Transactional
    public Admin atualizar(UUID id, Admin dadosAtualizados) {
        Admin admin = buscarPorId(id);

        admin.setNome(dadosAtualizados.getNome());
        admin.setEmail(dadosAtualizados.getEmail());

        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isBlank()) {
            admin.setSenha(encoder.encode(dadosAtualizados.getSenha()));
        }

        return repository.save(admin);
    }

    @Transactional
    public void deletar(UUID id) {
        Admin admin = buscarPorId(id);
        repository.delete(admin);
    }
}
