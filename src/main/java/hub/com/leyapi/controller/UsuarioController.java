package hub.com.leyapi.controller;

import hub.com.leyapi.apiResponse.GenericResponse;
import hub.com.leyapi.apiResponse.StatusApi;
import hub.com.leyapi.dto.usuario.UsuarioDTORequest;
import hub.com.leyapi.dto.usuario.UsuarioDTOResponse;
import hub.com.leyapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operaciones relacionadas con usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Listado usuarios", description = "Devuelve el listado completo del usuario.")
    @GetMapping
    ResponseEntity<List<UsuarioDTOResponse>> listUsuario(){
        List<UsuarioDTOResponse> list = usuarioService.ListUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(summary = "Busqueda de Usuario", description = "Devuelve el usuario buscado por el id.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado.")
    @ApiResponse(responseCode = "404", description = "Usuario no existe.")
    @GetMapping("/{id}")
    ResponseEntity<GenericResponse<UsuarioDTOResponse>> findUsuarios(@PathVariable Long id){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.findByIdUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(
                        StatusApi.SUCCESS,
                        List.of(usuarioDTOResponse)));
    }

    @Operation(summary = "Registrar Usuario", description = "Registra un usuario y retorna el usuario creado.")
    @ApiResponse(responseCode = "201", description = "Usuario creado.")
    @ApiResponse(responseCode = "400", description = "Error de validacion de datos.")
    @PostMapping
    ResponseEntity<GenericResponse<UsuarioDTOResponse>> createUsuario(@Valid  @RequestBody UsuarioDTORequest usuarioDTORequest){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.saveUsuario(usuarioDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(
                StatusApi.CREATED,
                List.of(usuarioDTOResponse)
        ));
    }

    @Operation(summary = "Modificar Usuario", description = "Modificar un usuario por id y retorna el usuario creado.")
    @ApiResponse(responseCode = "201", description = "Usuario modificacdo.")
    @ApiResponse(responseCode = "400", description = "Error de validacion de datos.")
    @PutMapping("/{id}")
    ResponseEntity<GenericResponse<UsuarioDTOResponse>> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTORequest usuarioDTORequest){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.updateUsuario(usuarioDTORequest, id);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(
                StatusApi.SUCCESS,
                List.of(usuarioDTOResponse)
        ));
    }
    @Operation(summary = "Eliminar Usuario", description = "Elimina el usuario buscado por el id.")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado.")
    @ApiResponse(responseCode = "404", description = "Usuario no existe.")
    @DeleteMapping("/{id}")
    ResponseEntity<GenericResponse<Void>> deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new GenericResponse<>(
                StatusApi.DELETED
        ));
    }

}