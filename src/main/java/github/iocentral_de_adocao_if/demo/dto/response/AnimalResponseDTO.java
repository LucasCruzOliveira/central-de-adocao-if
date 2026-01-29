package github.iocentral_de_adocao_if.demo.dto.response;

import java.util.List;
import java.util.UUID;

public record AnimalResponseDTO(
        UUID id,
        String nome,
        String especie,
        String raca,
        int idade,
        String sexo,
        String descricao,
        List<String> fotoUrls,
        boolean adotado
) {}
