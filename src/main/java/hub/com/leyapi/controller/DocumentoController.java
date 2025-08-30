package hub.com.leyapi.controller;

import hub.com.leyapi.dto.documento.DocumentoDTORequest;
import hub.com.leyapi.dto.documento.DocumentoDTOResponse;
import hub.com.leyapi.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    @GetMapping
    public ResponseEntity<List<DocumentoDTOResponse>> getAllDocumentos() {
        List<DocumentoDTOResponse> list = documentoService.ListDocumentos();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTOResponse> getDocumentoById(@PathVariable Long id) {
        DocumentoDTOResponse documentodto = documentoService.findbyIdDocumento(id);
        return ResponseEntity.status(HttpStatus.OK).body(documentodto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoDTOResponse> saveDocumento(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("numeroExpediente") String numeroExpediente,
            @RequestParam("idUsuario") Long idUsuario,
            @RequestPart("file") MultipartFile file) {

        DocumentoDTORequest request = new DocumentoDTORequest(
                titulo,
                descripcion,
                numeroExpediente,
                idUsuario
        );
        DocumentoDTOResponse documentodto = documentoService.saveDocumento(request,file);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentodto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDTOResponse> updateDocumento(@PathVariable Long id, @RequestBody DocumentoDTORequest request, @RequestParam("file") MultipartFile file) {
        DocumentoDTOResponse documentoDTOResponse = documentoService.updateDocumento(request, id,file);
        return ResponseEntity.status(HttpStatus.OK).body(documentoDTOResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        documentoService.deleteDocumento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    // add salve
    @PatchMapping("/estado/realizado/{id}")
    ResponseEntity<DocumentoDTOResponse> updateEstadoDocumento(@PathVariable Long id, @RequestParam String estado) {
        DocumentoDTOResponse documentodto = documentoService.updateEstadoDocumento(id,estado);
        return ResponseEntity.status(HttpStatus.OK).body(documentodto);
    }

    // add jpa
    @GetMapping("/doc/{id}")
    public ResponseEntity<List<DocumentoDTOResponse>> getAllDocumentos(@PathVariable Long id) {
        List<DocumentoDTOResponse> list = documentoService.ListDocumentosByUsuarioId(id);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @GetMapping("/doc/numero")
    public ResponseEntity<DocumentoDTOResponse> getDocumentoNumero(@RequestParam String numero) {
        DocumentoDTOResponse documentoDTOResponse = documentoService.findByIdDocumentoNumeroExpediente(numero);
        return ResponseEntity.status(HttpStatus.OK).body(documentoDTOResponse);
    }

    // listado por nombre de estado
    @GetMapping("/doc/estado")
    public ResponseEntity<List<DocumentoDTOResponse>> getDocumentoEstado(@RequestParam String estado) {
        List<DocumentoDTOResponse> list = documentoService.findByIdDocumentoEstado(estado);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}