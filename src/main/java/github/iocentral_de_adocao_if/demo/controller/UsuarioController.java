package github.iocentral_de_adocao_if.demo.controller;

import github.iocentral_de_adocao_if.demo.dto.request.UsuarioRequestDTO;
import github.iocentral_de_adocao_if.demo.dto.response.UsuarioResponseDTO;
import github.iocentral_de_adocao_if.demo.model.Usuario;
import github.iocentral_de_adocao_if.demo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> lista = service.listarTodos()
                .stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail(), u.getTelefone(), u.getVinculoIFPB()))
                .toList();

        return ResponseEntity.ok(lista);
    }

   @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscar(@PathVariable UUID id) {
        Usuario usuario = service.buscarPorId(id);
        return ResponseEntity.ok(
                new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(),usuario.getTelefone(),usuario.getVinculoIFPB())
        );
   }

   @PostMapping("/salvar")
    public ResponseEntity<UsuarioResponseDTO> salvar(@RequestBody UsuarioRequestDTO dto) {
        Usuario novo = new Usuario(dto.nome(), dto.email(), dto.senha(), dto.telefone(), dto.vinculoIFPB());
        Usuario salvo = service.salvar(novo);

       return ResponseEntity.ok(
                new UsuarioResponseDTO(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getTelefone(), salvo.getVinculoIFPB())
        );
   }

   @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable UUID id,
            @RequestBody UsuarioRequestDTO dto) {
        Usuario usuarioAtualizado = new Usuario(dto.nome(), dto.email(), dto.senha(), dto.telefone(), dto.vinculoIFPB());
        Usuario atualizado = service.atualizar(id, usuarioAtualizado);

        return ResponseEntity.ok(new UsuarioResponseDTO(atualizado.getId(), atualizado.getNome(), atualizado.getEmail(), atualizado.getTelefone(), atualizado.getVinculoIFPB())
        );
   }

   @PostMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
   }
}
