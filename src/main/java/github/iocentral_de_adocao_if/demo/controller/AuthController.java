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
import github.iocentral_de_adocao_if.demo.service.security.JwtService;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AdminRepository adminRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;


    public AuthController(
            AdminRepository adminRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder encoder,
             JwtService jwtService
    ) {
        this.adminRepository = adminRepository;
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {

        var adminOpt = adminRepository.findByEmail(dto.email());
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();

            if (!encoder.matches(dto.senha(), admin.getSenha())) {
                throw new RuntimeException("Email ou senha inválidos");
            }

            String token = jwtService.gerarToken(admin.getEmail(), "ADMIN");

            return ResponseEntity.ok(
                    new LoginResponseDTO(
                            admin.getId(),
                            admin.getNome(),
                            admin.getEmail(),
                            "ADMIN",
                            token
                    )
            );
        }

        var userOpt = usuarioRepository.findByEmail(dto.email());
        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();

            if (!encoder.matches(dto.senha(), user.getSenha())) {
                throw new RuntimeException("Email ou senha inválidos");
            }

            String token = jwtService.gerarToken(user.getEmail(), "USUARIO");

            return ResponseEntity.ok(
                    new LoginResponseDTO(
                            user.getId(),
                            user.getNome(),
                            user.getEmail(),
                            "USUARIO",
                            token
                    )
            );
        }

        throw new RuntimeException("Email ou senha inválidos");
    }

}
