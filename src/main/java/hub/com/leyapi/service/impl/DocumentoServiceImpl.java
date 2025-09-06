package hub.com.leyapi.service.impl;

import hub.com.leyapi.dto.documento.DocumentoDTORequest;
import hub.com.leyapi.dto.documento.DocumentoDTOResponse;
import hub.com.leyapi.mapper.DocumentoMapper;
import hub.com.leyapi.menss.NotifiacionService;
import hub.com.leyapi.model.Documento;
import hub.com.leyapi.model.Usuario;
import hub.com.leyapi.nums.DocumentoEstado;
import hub.com.leyapi.repo.DocumentoRepo;
import hub.com.leyapi.repo.UsuarioRepo;
import hub.com.leyapi.service.DocumentoService;
import hub.com.leyapi.util.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final NotifiacionService notifiacionService;

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

    @Transactional
    @Override
    public DocumentoDTOResponse saveDocumento(String titulo,String descripcion,String numeroExpediente,Long idUsuario, MultipartFile file) {

        // Buscar usuario
        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Documento documento = new Documento();
        documento.setUsuario(usuario);
        documento.setTitulo(titulo);
        documento.setDescripcion(descripcion);
        documento.setNumeroExpediente(numeroExpediente);
        documento.setEstado(DocumentoEstado.PROCESO);
        documento.setFechaEnvio(LocalDateTime.now());

        if (file != null && !file.isEmpty()){
            documento.setArchivoUrl(storageService.save(file));
            documento = documentoRepo.save(documento);
        }else {
            throw new RuntimeException("Archivo no encontrado");
        }

        return documentoMapper.toDocumentoDTOResponse(documento);
    }

    @Transactional
    @Override
    public DocumentoDTOResponse updateDocumento(String titulo,String descripcion,String numeroExpediente,Long idUsuario, Long idDocumento,MultipartFile file) {

        // buscar documento
        Documento documento = documentoRepo.findById(idDocumento)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        // buscar usuario
        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        documento.setUsuario(usuario);
        documento.setTitulo(titulo);
        documento.setDescripcion(descripcion);
        documento.setNumeroExpediente(numeroExpediente);

        // guardar archivo cargado
        if (file != null && !file.isEmpty()){
            documento.setArchivoUrl(storageService.save(file));
            documento = documentoRepo.save(documento);
        }else {
            throw new RuntimeException("Archivo no encontrado");
        }
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
// 01
//        // disparar notificacion
//        notifiacionService.notifyUsuario(
//                documento.getUsuario().getCorreo(),
//                "Estado : "+ documento.getEstado() + "su documento",
//                "Con el expediente : "+documento.getNumeroExpediente()
//                + " Cambio a estado : " + documento.getEstado()
//        );
// 02
//        // disparar notificacion con adjunto
//        Path filePath = Paths.get("uploads").resolve(documento.getArchivoUrl()).normalize();
//        Resource pdf = new FileSystemResource(filePath);
//
//        notifiacionService.notifyUsuarioWithAttachment(
//                documento.getUsuario().getCorreo(),
//                "Estado actualizado de su documento",
//                "El expediente " + documento.getNumeroExpediente() +
//                        " cambió a estado: " + documento.getEstado(),
//                pdf
//        );

        Path filePath = Paths.get("uploads").resolve(documento.getArchivoUrl()).normalize();
        Resource pdf = new FileSystemResource(filePath);

        String color = "black"; // default
        if (documento.getEstado().equals("REALIZADO")) {
            color = "#28a745"; // verde bootstrap
        } else if (documento.getEstado().equals("RECHAZADO")) {
            color = "#dc3545"; // rojo bootstrap
        }

// cuerpo del correo en HTML
        String htmlBody = """
    <html>
      <body style="font-family: Arial, sans-serif; color: #333;">
        <h2 style="color:#2E86C1;">📄 Notificación de Documento</h2>
        <p>Estimado <b>%s %s</b>,</p>
        <p>Su expediente <b>%s</b> ha cambiado al estado:</p>
        <p style="font-size:16px; color:%s;"><b>%s</b></p>
        <hr>
        <p>Puedes revisar el documento adjunto.</p>
        <p style="font-size:12px; color:gray;">
          Mesa de Partes Digital - Gobierno del Perú
        </p>
      </body>
    </html>
    """.formatted(
                documento.getUsuario().getNombre(),
                documento.getUsuario().getApellido(),
                documento.getNumeroExpediente(),
                color,
                documento.getEstado()
        );

        notifiacionService.notifyUsuarioGui(
                documento.getUsuario().getCorreo(),
                "Estado actualizado de su documento",
                htmlBody,
                pdf);

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

    @Override
    public Resource downloadDocumento(Long idDocumento) {
        Documento documento = documentoRepo.findById(idDocumento)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        try{
            Path filePath = Paths.get("uploads").resolve(documento.getArchivoUrl()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Documento no encontrado");
            }
            return resource;
        } catch (MalformedURLException e){
            throw new RuntimeException("Error cargando archivo", e);
        }
    }
    //


}
