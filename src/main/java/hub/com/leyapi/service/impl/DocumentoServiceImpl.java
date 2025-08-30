package hub.com.leyapi.service.impl;

import hub.com.leyapi.dto.documento.DocumentoDTORequest;
import hub.com.leyapi.dto.documento.DocumentoDTOResponse;
import hub.com.leyapi.mapper.DocumentoMapper;
import hub.com.leyapi.model.Documento;
import hub.com.leyapi.model.Usuario;
import hub.com.leyapi.nums.DocumentoEstado;
import hub.com.leyapi.repo.DocumentoRepo;
import hub.com.leyapi.repo.UsuarioRepo;
import hub.com.leyapi.service.DocumentoService;
import hub.com.leyapi.util.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoMapper documentoMapper;
    private final DocumentoRepo documentoRepo;
    private final UsuarioRepo usuarioRepo;
    private final StorageService storageService;

    @Override
    public List<DocumentoDTOResponse> ListDocumentos() {
        List<Documento> documentos = documentoRepo.findAll();
        return documentos.stream()
                .map(documento -> documentoMapper.toDocumentoDTOResponse(documento))
                .toList();
    }

    @Override
    public DocumentoDTOResponse findbyIdDocumento(Long idDocumento) {
        Documento documento = documentoRepo.findById(idDocumento)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        return documentoMapper.toDocumentoDTOResponse(documento);
    }

    @Override
    public DocumentoDTOResponse saveDocumento(DocumentoDTORequest request, MultipartFile file) {
        Documento documento = documentoMapper.toDocumento(request);
        Usuario usuario = usuarioRepo.findById(request.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // guardar archivo cargado
        if (file != null && !file.isEmpty()){
            String filename = storageService.save(file);
            documento.setArchivoUrl(filename);
        }

        documento.setUsuario(usuario);
        documento.setEstado(DocumentoEstado.PROCESO);
        documento.setFechaEnvio(LocalDateTime.now());


        documento = documentoRepo.save(documento);
        return documentoMapper.toDocumentoDTOResponse(documento);
    }

    @Override
    public DocumentoDTOResponse updateDocumento(DocumentoDTORequest request, Long idDocumento,MultipartFile file) {
        Documento documento = documentoRepo.findById(idDocumento)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        Usuario usuario = usuarioRepo.findById(request.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // guardar archivo cargado
        if (file != null && file.isEmpty()){
            String filename = storageService.save(file);
            documento.setArchivoUrl(filename);
        }

        documento.setUsuario(usuario);
        documento.setTitulo(request.titulo());
        documento.setDescripcion(request.descripcion());
        documento.setFechaEnvio(LocalDateTime.now());
        documento.setEstado(DocumentoEstado.PROCESO);
        documento.setNumeroExpediente(request.numeroExpediente());
        documento = documentoRepo.save(documento);
        return documentoMapper.toDocumentoDTOResponse(documento);
    }

    @Override
    public void deleteDocumento(Long idDocuemnto) {
        Documento documento = documentoRepo.findById(idDocuemnto)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        documentoRepo.delete(documento);

    }

    // update documento cambiar estado realizado
    @Override
    public DocumentoDTOResponse updateEstadoDocumento(Long idDocumento, String estado) {
        Documento documento = documentoRepo.findById(idDocumento)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        if (estado == null || estado.isBlank()){
            throw new IllegalArgumentException("ESTADO VACIO"+estado);
        }

        estado = estado.toUpperCase();
        if (estado.equals(DocumentoEstado.REALIZADO.toString())) {
            documento.setEstado(DocumentoEstado.REALIZADO);
        } else if (estado.equals(DocumentoEstado.RECHAZADO.toString())) {
            documento.setEstado(DocumentoEstado.RECHAZADO);
        } else {
            throw new IllegalArgumentException("ESTADO MAL ESCRITO : "+estado);
        }

        documento = documentoRepo.save(documento);
        return documentoMapper.toDocumentoDTOResponse(documento);
    }

    // add
    @Override
    public List<DocumentoDTOResponse> ListDocumentosByUsuarioId(Long idUsuario) {
        List<Documento> documentoList = documentoRepo.findByUsuarioIdUsuario(idUsuario);
        return documentoList.stream()
                .map(documento -> documentoMapper.toDocumentoDTOResponse(documento))
                .toList();
    }

    @Override
    public DocumentoDTOResponse findByIdDocumentoNumeroExpediente(String numeroExpediente) {
        Documento documento = documentoRepo.findByNumeroExpediente(numeroExpediente)
                .orElseThrow(() -> new RuntimeException("no existe el numero de documento"));
        return documentoMapper.toDocumentoDTOResponse(documento);
    }

    // listado de documentos por estado
    @Override
    public List<DocumentoDTOResponse> findByIdDocumentoEstado(String estado) {
        if (estado.isBlank()){
            throw new IllegalArgumentException("ESTADO VACIO : "+estado);
        }
        DocumentoEstado docEstado;
        try{
            docEstado = DocumentoEstado.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("ESTADO MAL ESCRITO : "+estado);
        }
        List<Documento> documentoList = documentoRepo.findByEstado(docEstado);
        return documentoList.stream().map(documento -> documentoMapper.toDocumentoDTOResponse(documento)).toList();

    }


}
