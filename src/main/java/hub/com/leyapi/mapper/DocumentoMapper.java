package hub.com.leyapi.mapper;

import hub.com.leyapi.dto.documento.DocumentoDTORequest;
import hub.com.leyapi.dto.documento.DocumentoDTOResponse;
import hub.com.leyapi.dto.usuario.UsuarioDTORequest;
import hub.com.leyapi.model.Documento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface DocumentoMapper {

    // Entity
    @Mapping(target = "usuario.idUsuario", source = "idUsuario")
    Documento toDocumento(DocumentoDTORequest documentoDTORequest);

    DocumentoDTOResponse toDocumentoDTOResponse(Documento documento);
}
