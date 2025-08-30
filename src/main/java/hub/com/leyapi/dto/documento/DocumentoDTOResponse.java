package hub.com.leyapi.dto.documento;

import hub.com.leyapi.dto.usuario.UsuarioDTOResponse;

import java.time.LocalDateTime;

public record DocumentoDTOResponse(
        Long idDocumento,
        String titulo,
        String descripcion,
        String archivoUrl,
        LocalDateTime fechaEnvio,
        String estado,
        String numeroExpediente,
        UsuarioDTOResponse usuario
) {}
