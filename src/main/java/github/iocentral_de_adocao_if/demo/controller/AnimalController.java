package github.iocentral_de_adocao_if.demo.controller;

import github.iocentral_de_adocao_if.demo.dto.request.AnimalRequestDTO;
import github.iocentral_de_adocao_if.demo.dto.response.AnimalResponseDTO;
import github.iocentral_de_adocao_if.demo.model.Animal;
import github.iocentral_de_adocao_if.demo.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponseDTO>> listar() {
        List<AnimalResponseDTO> lista = service.listarTodos()
                .stream()
                .map(a -> new AnimalResponseDTO(a.getId(), a.getNome() ,a.getEspecie(), a.getRaca(), a.getIdade(), a.getSexo() ,a.getDescricao(), a.getFotoUrl(), a.isAdotado()))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> buscar(@PathVariable UUID id) {
        Animal animal = service.buscarPorId(id);
        return ResponseEntity.ok(
                new AnimalResponseDTO(animal.getId(), animal.getNome() ,animal.getEspecie(), animal.getRaca(), animal.getIdade(), animal.getSexo() ,animal.getDescricao(), animal.getFotoUrl(), animal.isAdotado())
        );
    }

    @PostMapping
    public ResponseEntity<AnimalResponseDTO> salvar(@RequestBody AnimalRequestDTO dto) {
        Animal novo = new Animal(dto.nome(), dto.especie(), dto.raca(), dto.idade(), dto.sexo(), dto.descricao(), dto.fotoUrl());
        Animal salvo = service.salvar(novo);

        return ResponseEntity.ok(new AnimalResponseDTO(salvo.getId(), salvo.getNome(), salvo.getEspecie(), salvo.getRaca(), salvo.getIdade(), salvo.getSexo(), salvo.getDescricao(), salvo.getFotoUrl(), salvo.isAdotado())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> atualizar(
            @PathVariable UUID id,
            @RequestBody AnimalRequestDTO dto
    ) {
        Animal animalAtualizado = new Animal(dto.nome(), dto.especie(), dto.raca(), dto.idade(), dto.sexo(), dto.descricao(), dto.fotoUrl());
        Animal atualizado = service.atualizar(id, animalAtualizado);

        return ResponseEntity.ok(
                new AnimalResponseDTO(atualizado.getId(), atualizado.getNome(), atualizado.getEspecie(), atualizado.getRaca(), atualizado.getIdade(), atualizado.getSexo(), atualizado.getDescricao(), atualizado.getFotoUrl(), atualizado.isAdotado())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> remover(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
