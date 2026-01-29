package github.iocentral_de_adocao_if.demo.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AdocaoAdminResponseDTO(
        UUID id,
        LocalDate dataAdocao,

        UUID animalId,
        String animalNome,
        List<String> animalFotos,

        String nome,
        String email,
        String telefone,
        String vinculo
) {}