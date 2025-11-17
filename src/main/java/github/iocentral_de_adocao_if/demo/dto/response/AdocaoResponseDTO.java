package github.iocentral_de_adocao_if.demo.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record AdocaoResponseDTO(
        UUID id,
        UUID usuarioId,
        UUID animalId,
        LocalDate dataAdocao
) {}
