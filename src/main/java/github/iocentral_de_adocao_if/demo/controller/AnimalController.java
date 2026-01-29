package github.iocentral_de_adocao_if.demo.controller;

import github.iocentral_de_adocao_if.demo.dto.request.AnimalRequestDTO;
import github.iocentral_de_adocao_if.demo.dto.response.AnimalResponseDTO;
import github.iocentral_de_adocao_if.demo.model.Animal;
import github.iocentral_de_adocao_if.demo.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animais")
@CrossOrigin
public class AnimalController {

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public List<AnimalResponseDTO> listar() {
        return service.listarTodos().stream()
                .map(a -> new AnimalResponseDTO(
                        a.getId(),
                        a.getNome(),
                        a.getEspecie(),
                        a.getRaca(),
                        a.getIdade(),
                        a.getSexo(),
                        a.getDescricao(),
                        // AQUI: Assumindo que você alterou o Model para ter getFotoUrls()
                        a.getFotoUrls(),
                        a.isAdotado()
                )).toList();
    }

    @PostMapping(value = "/salvar", consumes = "multipart/form-data")
    public AnimalResponseDTO salvar(
            @RequestPart("dados") AnimalRequestDTO dto,
            // AQUI: Mudamos para List e nome "fotos"
            @RequestPart("fotos") List<MultipartFile> fotos
    ) {
        // Você precisará atualizar seu Service para aceitar List<MultipartFile>
        Animal a = service.salvar(dto, fotos);

        return new AnimalResponseDTO(
                a.getId(),
                a.getNome(),
                a.getEspecie(),
                a.getRaca(),
                a.getIdade(),
                a.getSexo(),
                a.getDescricao(),
                a.getFotoUrls(), // Retorna lista
                a.isAdotado()
        );
    }

    @PutMapping(value = "/atualizar/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Void> atualizar(
            @PathVariable UUID id,
            @RequestPart("dados") AnimalRequestDTO dto,
            // AQUI: List e required false (caso não envie novas fotos)
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos
    ) {
        service.atualizar(id, dto, fotos);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}