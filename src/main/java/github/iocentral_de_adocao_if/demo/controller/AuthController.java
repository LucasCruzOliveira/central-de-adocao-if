package github.iocentral_de_adocao_if.demo.controller;

import github.iocentral_de_adocao_if.demo.dto.request.LoginRequestDTO;
import github.iocentral_de_adocao_if.demo.dto.response.LoginResponseDTO;
import github.iocentral_de_adocao_if.demo.model.Admin;
import github.iocentral_de_adocao_if.demo.repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;

    public AuthController(AdminRepository adminRepository,
                          PasswordEncoder encoder) {
        this.adminRepository = adminRepository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {

        System.out.println("===== LOGIN =====");
        System.out.println("EMAIL RECEBIDO: [" + dto.email() + "]");
        System.out.println("SENHA RECEBIDA: [" + dto.senha() + "]");

        Admin admin = adminRepository.findByEmail(dto.email())
                .orElseThrow(() -> {
                    System.out.println(" EMAIL NÃO ENCONTRADO");
                    return new RuntimeException("Email ou senha inválidos");
                });

        System.out.println("EMAIL DO BANCO: [" + admin.getEmail() + "]");
        System.out.println("SENHA DO BANCO: [" + admin.getSenha() + "]");

        if (!encoder.matches(dto.senha(), admin.getSenha())) {
            System.out.println(" SENHA NÃO CONFERE");
            throw new RuntimeException("Email ou senha inválidos");
        }

        System.out.println("LOGIN OK");

        return ResponseEntity.ok(
                new LoginResponseDTO(
                        admin.getId(),
                        admin.getNome(),
                        admin.getEmail()
                )
        );
    }
}

