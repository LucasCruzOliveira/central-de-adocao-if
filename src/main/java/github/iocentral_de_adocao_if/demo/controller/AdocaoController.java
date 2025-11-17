package github.iocentral_de_adocao_if.demo.controller;

import github.iocentral_de_adocao_if.demo.dto.request.AdocaoRequestDTO;
import github.iocentral_de_adocao_if.demo.dto.response.AdocaoResponseDTO;
import github.iocentral_de_adocao_if.demo.service.AdocaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    private final AdocaoService service;

    public AdocaoController(AdocaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AdocaoResponseDTO>> listar() {
        var lista = service.listarTodas()
                .stream()
                .map(a -> new AdocaoResponseDTO(
                        a.getId(),
                        a.getUsuario().getId(),
                        a.getAnimal().getId(),
                        a.getDataAdocao()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdocaoResponseDTO> buscar(@PathVariable UUID id) {
        var adocao = service.buscarPorId(id);

        return ResponseEntity.ok(
                new AdocaoResponseDTO(
                        adocao.getId(),
                        adocao.getUsuario().getId(),
                        adocao.getAnimal().getId(),
                        adocao.getDataAdocao()
                )
        );
    }

    @PostMapping
    public ResponseEntity<AdocaoResponseDTO> criar(@RequestBody AdocaoRequestDTO dto) {
        var salvo = service.salvar(dto);

        return ResponseEntity.ok(
                new AdocaoResponseDTO(
                        salvo.getId(),
                        salvo.getUsuario().getId(),
                        salvo.getAnimal().getId(),
                        salvo.getDataAdocao()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

