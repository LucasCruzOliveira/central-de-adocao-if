package github.iocentral_de_adocao_if.demo.controller;

import github.iocentral_de_adocao_if.demo.dto.request.LoginRequestDTO;
import github.iocentral_de_adocao_if.demo.dto.response.LoginResponseDTO;
import github.iocentral_de_adocao_if.demo.model.Admin;
import github.iocentral_de_adocao_if.demo.model.Usuario;
import github.iocentral_de_adocao_if.demo.repository.AdminRepository;
import github.iocentral_de_adocao_if.demo.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AdminRepository adminRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public AuthController(
            AdminRepository adminRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder encoder
    ) {
        this.adminRepository = adminRepository;
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {

        // 🔐 LOGIN ADMIN
        var adminOpt = adminRepository.findByEmail(dto.email());
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();

            if (!encoder.matches(dto.senha(), admin.getSenha())) {
                throw new RuntimeException("Email ou senha inválidos");
            }

            return ResponseEntity.ok(
                    new LoginResponseDTO(
                            admin.getId(),
                            admin.getNome(),
                            admin.getEmail(),
                            "ADMIN"
                    )
            );
        }

        // 👤 LOGIN USUÁRIO
        var userOpt = usuarioRepository.findByEmail(dto.email());
        if (userOpt.isPresent()) {
            Usuario usuario = userOpt.get();

            if (!encoder.matches(dto.senha(), usuario.getSenha())) {
                throw new RuntimeException("Email ou senha inválidos");
            }

            return ResponseEntity.ok(
                    new LoginResponseDTO(
                            usuario.getId(),
                            usuario.getNome(),
                            usuario.getEmail(),
                            "USUARIO"
                    )
            );
        }

        throw new RuntimeException("Email ou senha inválidos");
    }
}
