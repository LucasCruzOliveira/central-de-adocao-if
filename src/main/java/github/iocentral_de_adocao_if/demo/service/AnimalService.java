package github.iocentral_de_adocao_if.demo.service;

import github.iocentral_de_adocao_if.demo.model.Animal;
import github.iocentral_de_adocao_if.demo.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Animal salvar(Animal animal) {
        return repository.save(animal);
    }

    @Transactional
    public Animal atualizar(UUID id, Animal dadosAtualizados) {
        Animal animal = buscarPorId(id);

        animal.setNome(dadosAtualizados.getNome());
        animal.setEspecie(dadosAtualizados.getEspecie());
        animal.setRaca(dadosAtualizados.getRaca());
        animal.setSexo(dadosAtualizados.getSexo());
        animal.setIdade(dadosAtualizados.getIdade());
        animal.setFotoUrl(dadosAtualizados.getFotoUrl());
        animal.setDescricao(dadosAtualizados.getDescricao());

        return repository.save(animal);
    }

    @Transactional
    public void deletar(UUID id) {
        Animal animal = buscarPorId(id);
        repository.delete(animal);
    }

}
