package hub.com.leyapi.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO request base de Usuario")
public record UsuarioDTORequest(

        @Schema(description = "Nombre del usuario", example = "Alexis")
        @NotBlank(message = "{field.required}")
        @Size(max=50,min=2,message = "{field.size.range}")
        @Pattern(regexp = "^[\\p{L} ]+$", message = "{field.invalid.format}")
        String nombre,


        @Schema(description = "Apellido del usuario", example = "Alexis")
        @NotBlank(message = "{field.required}")
        @Size(max=50,min=2,message = "{field.size.range}")
        @Pattern(regexp = "^[\\p{L} ]+$", message = "{field.invalid.format}")
        String apellido,

        @Schema(description = "Dni del usuario", example = "12345678")
        @NotBlank(message = "{field.required}")
        @Pattern(regexp = "^[0-9]{8}$", message = "{field.invalid.format}")
        String dni,

        @Schema(description = "Correo del usuario", example = "alexis12@gmail.com")
        @NotBlank(message = "{field.required}")
        @Size(max = 100, min = 2, message = "{field.size.range}")
        @Email(message = "{field.email.invalid}")
        String correo
) {}