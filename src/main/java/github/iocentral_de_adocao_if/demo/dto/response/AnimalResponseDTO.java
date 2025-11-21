package github.iocentral_de_adocao_if.demo.dto.response;

import java.util.UUID;

public record AnimalResponseDTO(
        UUID id,
        String nome,
        String especie,
        String raca,
        int idade,
        String sexo,
        String descricao,
        String fotoUrl,
        boolean adotado
) {
}
