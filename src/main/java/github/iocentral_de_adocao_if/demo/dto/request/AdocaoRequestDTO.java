package github.iocentral_de_adocao_if.demo.dto.request;

import java.util.UUID;

public record AdocaoRequestDTO(
        UUID usuarioId,
        UUID animalId
) {}

