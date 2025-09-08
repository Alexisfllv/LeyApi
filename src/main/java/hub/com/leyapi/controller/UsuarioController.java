package hub.com.leyapi.controller;

import hub.com.leyapi.apiResponse.GenericResponse;
import hub.com.leyapi.apiResponse.StatusApi;
import hub.com.leyapi.dto.usuario.UsuarioDTORequest;
import hub.com.leyapi.dto.usuario.UsuarioDTOResponse;
import hub.com.leyapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    ResponseEntity<List<UsuarioDTOResponse>> listUsuario(){
        List<UsuarioDTOResponse> list = usuarioService.ListUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{id}")
    ResponseEntity<GenericResponse<UsuarioDTOResponse>> findUsuarios(@PathVariable Long id){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.findByIdUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(
                        StatusApi.SUCCESS,
                        List.of(usuarioDTOResponse)));
    }

    @PostMapping
    ResponseEntity<GenericResponse<UsuarioDTOResponse>> createUsuario(@Valid  @RequestBody UsuarioDTORequest usuarioDTORequest){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.saveUsuario(usuarioDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(
                StatusApi.CREATED,
                List.of(usuarioDTOResponse)
        ));
    }

    @PutMapping("/{id}")
    ResponseEntity<GenericResponse<UsuarioDTOResponse>> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTORequest usuarioDTORequest){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.updateUsuario(usuarioDTORequest, id);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(
                StatusApi.SUCCESS,
                List.of(usuarioDTOResponse)
        ));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<GenericResponse<Void>> deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new GenericResponse<>(
                StatusApi.DELETED
        ));
    }

}