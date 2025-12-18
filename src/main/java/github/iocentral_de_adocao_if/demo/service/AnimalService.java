package github.iocentral_de_adocao_if.demo.service;

import github.iocentral_de_adocao_if.demo.dto.request.AnimalRequestDTO;
import github.iocentral_de_adocao_if.demo.model.Animal;
import github.iocentral_de_adocao_if.demo.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AnimalService {

    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public List<Animal> listarTodos() {
        return repository.findAll();
    }

    public Animal buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    @Transactional
    public Animal salvar(AnimalRequestDTO dto, MultipartFile foto) {
        try {
            String nomeArquivo = UUID.randomUUID() + "_" + foto.getOriginalFilename();
            Path caminho = Paths.get("uploads/" + nomeArquivo);

            Files.createDirectories(caminho.getParent());
            Files.write(caminho, foto.getBytes());

            Animal animal = new Animal(
                    dto.nome(),
                    dto.especie(),
                    dto.raca(),
                    dto.idade(),
                    dto.sexo(),
                    dto.descricao(),
                    "/uploads/" + nomeArquivo
            );

            return repository.save(animal);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }
    }

    @Transactional
    public Animal atualizar(UUID id, AnimalRequestDTO dto, MultipartFile foto) {

        Animal animal = buscarPorId(id);

        animal.setNome(dto.nome());
        animal.setEspecie(dto.especie());
        animal.setRaca(dto.raca());
        animal.setIdade(dto.idade());
        animal.setSexo(dto.sexo());
        animal.setDescricao(dto.descricao());

        if (foto != null && !foto.isEmpty()) {
            try {
                String nomeArquivo = UUID.randomUUID() + "_" + foto.getOriginalFilename();
                Path caminho = Paths.get("uploads/" + nomeArquivo);

                Files.createDirectories(caminho.getParent());
                Files.write(caminho, foto.getBytes());

                animal.setFotoUrl("/uploads/" + nomeArquivo);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao atualizar imagem", e);
            }
        }

        return repository.save(animal);
    }


    @Transactional
    public void deletar(UUID id) {
        repository.delete(buscarPorId(id));
    }
}
