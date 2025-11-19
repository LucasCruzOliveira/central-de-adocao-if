package github.iocentral_de_adocao_if.demo.service;

import github.iocentral_de_adocao_if.demo.model.Admin;
import github.iocentral_de_adocao_if.demo.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final AdminRepository repository;
    private final PasswordEncoder encoder;

    public AdminService(AdminRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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
        if (repository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Email já está em uso!");
        }

        //CRIPTOGRAFAÇÃO DA SENHA
        admin.setSenha(encoder.encode(admin.getSenha()));

        return repository.save(admin);
    }

    @Transactional
    public Admin atualizar(UUID id, Admin dadosAtualizados) {
        Admin admin = buscarPorId(id);

        admin.setNome(dadosAtualizados.getNome());
        admin.setEmail(dadosAtualizados.getEmail());

        // Só atualiza a senha se veio algo
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