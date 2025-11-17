package github.iocentral_de_adocao_if.demo.dto.response;

import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String email,
        String senha
) {
}
