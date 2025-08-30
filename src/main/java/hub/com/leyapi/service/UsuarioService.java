package hub.com.leyapi.service;

import hub.com.leyapi.dto.usuario.UsuarioDTORequest;
import hub.com.leyapi.dto.usuario.UsuarioDTOResponse;

import java.util.List;

public interface UsuarioService {
    // get
    List<UsuarioDTOResponse> ListUsuarios();
    UsuarioDTOResponse findByIdUsuario(Long idUsuario);
    // post
    UsuarioDTOResponse saveUsuario(UsuarioDTORequest request);
    UsuarioDTOResponse updateUsuario(UsuarioDTORequest request, Long idUsuario);
    void deleteUsuario(Long idUsuario);
}