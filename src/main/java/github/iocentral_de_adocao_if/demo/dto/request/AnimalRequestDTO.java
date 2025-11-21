package github.iocentral_de_adocao_if.demo.dto.request;

public record AnimalRequestDTO(
        String nome,
        String especie,
        String raca,
        int idade,
        String sexo,
        String descricao,
        String fotoUrl
) {
}
