package hub.com.leyapi.dto.usuario;

public record UsuarioDTORequest(
        String nombre,
        String apellido,
        String dni,
        String correo
) {}