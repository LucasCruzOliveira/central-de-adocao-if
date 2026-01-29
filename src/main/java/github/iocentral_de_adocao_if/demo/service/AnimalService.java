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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnimalService {

    private final AnimalRepository repository;
    private final String UPLOAD_DIR = "uploads/";

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
    public Animal salvar(AnimalRequestDTO dto, List<MultipartFile> fotos) {
        List<String> caminhosDasFotos = processarFotos(fotos);

        Animal animal = new Animal(
                dto.nome(),
                dto.especie(),
                dto.raca(),
                dto.idade(),
                dto.sexo(),
                dto.descricao(),
                caminhosDasFotos // Agora passando a lista completa!
        );

        return repository.save(animal);
    }

    @Transactional
    public Animal atualizar(UUID id, AnimalRequestDTO dto, List<MultipartFile> fotos) {
        Animal animal = buscarPorId(id);

        animal.setNome(dto.nome());
        animal.setEspecie(dto.especie());
        animal.setRaca(dto.raca());
        animal.setIdade(dto.idade());
        animal.setSexo(dto.sexo());
        animal.setDescricao(dto.descricao());

        // Se o usuário enviou novas fotos, nós processamos e substituímos
        if (fotos != null && !fotos.isEmpty() && !fotos.get(0).isEmpty()) {
            List<String> novosCaminhos = processarFotos(fotos);
            animal.setFotoUrls(novosCaminhos);
        }

        return repository.save(animal);
    }

    // Método auxiliar para não repetir código de salvar arquivo
    private List<String> processarFotos(List<MultipartFile> fotos) {
        List<String> caminhos = new ArrayList<>();

        if (fotos == null) return caminhos;

        for (MultipartFile foto : fotos) {
            if (foto.isEmpty()) continue;

            try {
                String nomeArquivo = UUID.randomUUID() + "_" + foto.getOriginalFilename();
                Path caminho = Paths.get(UPLOAD_DIR + nomeArquivo);

                Files.createDirectories(caminho.getParent());
                Files.write(caminho, foto.getBytes());

                caminhos.add("/" + UPLOAD_DIR + nomeArquivo);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar imagem: " + foto.getOriginalFilename(), e);
            }
        }
        return caminhos;
    }

    @Transactional
    public void deletar(UUID id) {
        repository.delete(buscarPorId(id));
    }
}