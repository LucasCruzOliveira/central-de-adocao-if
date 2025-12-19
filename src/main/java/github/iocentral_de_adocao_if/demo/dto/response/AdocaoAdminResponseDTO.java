package github.iocentral_de_adocao_if.demo.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record AdocaoAdminResponseDTO(
        UUID id,
        LocalDate dataAdocao,

        UUID animalId,
        String animalNome,
        String animalFoto,

        String nome,
        String email,
        String telefone,
        String vinculo
) {}
