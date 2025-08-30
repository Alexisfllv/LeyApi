package hub.com.leyapi.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioDTOResponse(
        Long idUsuario,
        String nombre,
        String apellido,
        String dni,
        String correo,
        LocalDateTime fechaRegistro
) {}