package github.iocentral_de_adocao_if.demo.service;

import github.iocentral_de_adocao_if.demo.model.Role;
import github.iocentral_de_adocao_if.demo.model.Usuario;
import github.iocentral_de_adocao_if.demo.repository.RoleRepository;
import github.iocentral_de_adocao_if.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import github.iocentral_de_adocao_if.demo.repository.AdminRepository;


import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;


    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder, RoleRepository roleRepository, AdminRepository adminRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;

    }

    public List<Usuario> listarTodos() {return repository.findAll();}

    public Usuario buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        if (repository.existsByEmail(usuario.getEmail())
                || adminRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já está em uso por outro usuário do sistema!");
        }


        usuario.setSenha(encoder.encode(usuario.getSenha()));

        switch (usuario.getVinculoIFPB().toUpperCase()){
            case "ALUNO":
                Role aluno = roleRepository.findById(1)
                        .orElseThrow(() -> new RuntimeException("Role não encontrada"));

                usuario.getRoles().add(aluno);
                break;

            case "PROFESSOR":
                Role professor = roleRepository.findById(2)
                        .orElseThrow(() -> new RuntimeException("Role não encontrada"));

                usuario.getRoles().add(professor);
                break;

            case "FUNCIONARIO":
                Role funcionario = roleRepository.findById(3)
                        .orElseThrow(() -> new RuntimeException("Role não encontrada"));

                usuario.getRoles().add(funcionario);
                break;

            case "VISITANTE":
                Role visitante = roleRepository.findById(4)
                        .orElseThrow(() -> new RuntimeException("Role não encontrada"));

                usuario.getRoles().add(visitante);
        }

        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(UUID id, Usuario dadosAtualizados) {
        Usuario usuario = buscarPorId(id);

        if (!usuario.getEmail().equals(dadosAtualizados.getEmail())) {
            if (repository.existsByEmail(dadosAtualizados.getEmail())
                    || adminRepository.existsByEmail(dadosAtualizados.getEmail())) {
                throw new RuntimeException("Email já está em uso por outro usuário do sistema!");
            }
        }

        usuario.setNome(dadosAtualizados.getNome());
        usuario.setEmail(dadosAtualizados.getEmail());

        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isBlank()) {
            usuario.setSenha(encoder.encode(dadosAtualizados.getSenha()));
        }

        return repository.save(usuario);
    }


    @Transactional
    public void deletar(UUID id) {
        Usuario usuario = buscarPorId(id);
        repository.delete(usuario);
    }
}
