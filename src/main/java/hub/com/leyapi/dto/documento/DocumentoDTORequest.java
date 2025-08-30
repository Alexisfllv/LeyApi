package hub.com.leyapi.dto.documento;

public record DocumentoDTORequest(
        String titulo,
        String descripcion,
        String archivoUrl,
        String numeroExpediente,
        Long idUsuario
) {}
