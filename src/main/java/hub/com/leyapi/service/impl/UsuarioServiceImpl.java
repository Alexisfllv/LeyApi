package hub.com.leyapi.service.impl;

import hub.com.leyapi.dto.usuario.UsuarioDTORequest;
import hub.com.leyapi.dto.usuario.UsuarioDTOResponse;
import hub.com.leyapi.exception.ResourceNotFoundException;
import hub.com.leyapi.mapper.UsuarioMapper;
import hub.com.leyapi.model.Usuario;
import hub.com.leyapi.repo.UsuarioRepo;
import hub.com.leyapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepo usuarioRepo;

    @Override
    public List<UsuarioDTOResponse> ListUsuarios() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        return usuarios.stream()
                .map(usuario -> usuarioMapper.toUsuarioDTOResponse(usuario))
                .toList();
    }

    @Override
    public UsuarioDTOResponse findByIdUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
        return usuarioMapper.toUsuarioDTOResponse(usuario);
    }

    @Override
    public UsuarioDTOResponse saveUsuario(UsuarioDTORequest request) {
        Usuario usuario = usuarioMapper.toUsuario(request);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuarioRepo.save(usuario);
        return usuarioMapper.toUsuarioDTOResponse(usuario);
    }

    @Override
    public UsuarioDTOResponse updateUsuario(UsuarioDTORequest request, Long idUsuario) {
        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setDni(request.dni());
        usuario.setCorreo(request.correo());
        usuario.setFechaRegistro(LocalDateTime.now());

        usuarioRepo.save(usuario);
        return usuarioMapper.toUsuarioDTOResponse(usuario);
    }

    @Override
    public void deleteUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
        usuarioRepo.delete(usuario);
    }
}
