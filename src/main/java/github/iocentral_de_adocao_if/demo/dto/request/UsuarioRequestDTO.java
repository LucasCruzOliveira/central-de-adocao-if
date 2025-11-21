package github.iocentral_de_adocao_if.demo.dto.request;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha,
        String telefone,
        String vinculoIFPB
) {}
