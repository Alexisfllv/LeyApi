package hub.com.leyapi.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
@Schema(description = "DTO response base de Usuario")
public record UsuarioDTOResponse(
        Long idUsuario,
        String nombre,
        String apellido,
        String dni,
        String correo,
        LocalDateTime fechaRegistro
) {}