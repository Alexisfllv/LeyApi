package hub.com.leyapi.service;

import hub.com.leyapi.dto.documento.DocumentoDTORequest;
import hub.com.leyapi.dto.documento.DocumentoDTOResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentoService {
    // get
    List<DocumentoDTOResponse> ListDocumentos();
    DocumentoDTOResponse findbyIdDocumento(Long idDocumento);
    // post
    DocumentoDTOResponse saveDocumento(DocumentoDTORequest request, MultipartFile multipartFile);
    DocumentoDTOResponse updateDocumento(DocumentoDTORequest request, Long idDocumento, MultipartFile multipartFile);
    void deleteDocumento(Long idDocuemnto);

    // modificar documento cambiar de estado : ""
    DocumentoDTOResponse updateEstadoDocumento(Long idDocumento , String estado);
    // adicionales
    // litado de documentos por nombre de usuario
    List<DocumentoDTOResponse> ListDocumentosByUsuarioId(Long idUsuario);

    // busqueda de documentos por numeroExpediente
    DocumentoDTOResponse findByIdDocumentoNumeroExpediente(String numeroExpediente);

    // listado de documentos por tipo de estado
    List<DocumentoDTOResponse> findByIdDocumentoEstado(String estado);


}