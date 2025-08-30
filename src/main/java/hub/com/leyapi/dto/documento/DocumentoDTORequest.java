package hub.com.leyapi.dto.documento;

public record DocumentoDTORequest(
        String titulo,
        String descripcion,
        String numeroExpediente,
        Long idUsuario
) {}
