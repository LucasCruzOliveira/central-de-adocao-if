package github.iocentral_de_adocao_if.demo.service;

import github.iocentral_de_adocao_if.demo.dto.request.AdocaoRequestDTO;
import github.iocentral_de_adocao_if.demo.model.Adocao;
import github.iocentral_de_adocao_if.demo.repository.AdocaoRepository;
import github.iocentral_de_adocao_if.demo.repository.AnimalRepository;
import github.iocentral_de_adocao_if.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AdocaoService {

    private final AdocaoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final AnimalRepository animalRepository;

    public AdocaoService(AdocaoRepository repository,
                         UsuarioRepository usuarioRepository,
                         AnimalRepository animalRepository) {

        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.animalRepository = animalRepository;
    }

    public List<Adocao> listarTodas() {
        return repository.findAll();
    }

    public Adocao buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adoção não encontrada"));
    }

    @Transactional
    public Adocao salvar(AdocaoRequestDTO dto) {

        var usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var animal = animalRepository.findById(dto.animalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        Adocao adocao = new Adocao();
        adocao.setUsuario(usuario);
        adocao.setAnimal(animal);
        adocao.setDataAdocao(LocalDate.now());

        return repository.save(adocao);
    }

    @Transactional
    public void deletar(UUID id) {
        Adocao adocao = buscarPorId(id);
        repository.delete(adocao);
    }
}
