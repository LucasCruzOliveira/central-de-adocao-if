package github.iocentral_de_adocao_if.demo.dto;

import java.util.UUID;

public record AdminResponseDTO(
        UUID id,
        String nome,
        String email
) {}

