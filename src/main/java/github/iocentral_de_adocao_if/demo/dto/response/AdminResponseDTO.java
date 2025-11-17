package github.iocentral_de_adocao_if.demo.dto.response;

import java.util.UUID;

public record AdminResponseDTO(
        UUID id,
        String nome,
        String email
) {}

