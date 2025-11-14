package github.iocentral_de_adocao_if.demo.controller;

import github.iocentral_de_adocao_if.demo.dto.AdminRequestDTO;
import github.iocentral_de_adocao_if.demo.dto.AdminResponseDTO;
import github.iocentral_de_adocao_if.demo.model.Admin;
import github.iocentral_de_adocao_if.demo.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> listar() {
        List<AdminResponseDTO> lista = service.listarTodos()
                .stream()
                .map(a -> new AdminResponseDTO(a.getId(), a.getNome(), a.getEmail()))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> buscar(@PathVariable UUID id) {
        Admin admin = service.buscarPorId(id);
        return ResponseEntity.ok(
                new AdminResponseDTO(admin.getId(), admin.getNome(), admin.getEmail())
        );
    }

    @PostMapping
    public ResponseEntity<AdminResponseDTO> salvar(@RequestBody AdminRequestDTO dto) {
        Admin novo = new Admin(dto.nome(), dto.email(), dto.senha());
        Admin salvo = service.salvar(novo);

        return ResponseEntity.ok(
                new AdminResponseDTO(salvo.getId(), salvo.getNome(), salvo.getEmail())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> atualizar(
            @PathVariable UUID id,
            @RequestBody AdminRequestDTO dto
    ) {
        Admin adminAtualizado = new Admin(dto.nome(), dto.email(), dto.senha());
        Admin atualizado = service.atualizar(id, adminAtualizado);

        return ResponseEntity.ok(
                new AdminResponseDTO(atualizado.getId(), atualizado.getNome(), atualizado.getEmail())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

