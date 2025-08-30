package hub.com.leyapi.mapper;

import hub.com.leyapi.dto.usuario.UsuarioDTORequest;
import hub.com.leyapi.dto.usuario.UsuarioDTOResponse;
import hub.com.leyapi.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Entity
    Usuario toUsuario(UsuarioDTORequest usuarioDTORequest);

    UsuarioDTOResponse toUsuarioDTOResponse(Usuario usuario);
}
