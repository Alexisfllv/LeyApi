package hub.com.leyapi.controller;

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
    ResponseEntity<UsuarioDTOResponse> listUsuarios(@PathVariable Long id){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.findByIdUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTOResponse);
    }

    @PostMapping
    ResponseEntity<UsuarioDTOResponse> createUsuario(@Valid  @RequestBody UsuarioDTORequest usuarioDTORequest){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.saveUsuario(usuarioDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTOResponse);
    }

    @PutMapping("/{id}")
    ResponseEntity<UsuarioDTOResponse> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTORequest usuarioDTORequest){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.updateUsuario(usuarioDTORequest, id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTOResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}