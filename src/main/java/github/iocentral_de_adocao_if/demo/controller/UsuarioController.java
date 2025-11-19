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

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> lista = service.listarTodos()
                .stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail()))
                .toList();

        return ResponseEntity.ok(lista);
    }

   @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscar(@PathVariable UUID id) {
        Usuario usuario = service.buscarPorId(id);
        return ResponseEntity.ok(
                new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail())
        );
   }

//   @PostMapping
//    public ResponseEntity<UsuarioResponseDTO> salvar(@RequestBody UsuarioRequestDTO dto) {
//        Usuario novo = new Usuario(dto.id(), dto.nome(), dto.email());
//        Usuario salvo = service.salvar(novo);
//
//        return ResponseEntity.ok(
//                new UsuarioResponseDTO(salvo.getId(), salvo.getNome(), salvo.getEmail())
//        );
//   }
}
