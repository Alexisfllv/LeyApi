package hub.com.leyapi.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioDTORequest(

        @NotBlank(message = "{field.required}")
        @Size(max=50,min=2,message = "{field.size.range}")
        @Pattern(regexp = "^[\\p{L} ]+$", message = "{field.invalid.format}")
        String nombre,


        @NotBlank(message = "{field.required}")
        @Size(max=50,min=2,message = "{field.size.range}")
        @Pattern(regexp = "^[\\p{L} ]+$", message = "{field.invalid.format}")
        String apellido,


        @NotBlank(message = "{field.required}")
        @Pattern(regexp = "^[0-9]{8}$", message = "{field.invalid.format}")
        String dni,

        @NotBlank(message = "{field.required}")
        @Size(max = 100, min = 2, message = "{field.size.range}")
        @Email(message = "{field.email.invalid}")
        String correo
) {}