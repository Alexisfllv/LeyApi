package hub.com.leyapi.controller;


import hub.com.leyapi.dto.actas.ActaDTORequest;
import hub.com.leyapi.dto.actas.ActaDTOResponse;
import hub.com.leyapi.service.ActaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actas")
@RequiredArgsConstructor
public class ActaController {

    private final ActaService actaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ActaDTOResponse> saveActa(
            @RequestParam("titulo") String titulo,
            @RequestPart List<MultipartFile> files) {
        ActaDTOResponse res =  actaService.saveActa(titulo, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // descargar resource zip
    @GetMapping("/{idActa}/download")
    public ResponseEntity<Resource> downloadActa(@PathVariable Long idActa) {

        Resource resource = actaService.downloadAllFiles(idActa);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=acta_" + idActa+".zip")
                .body(resource);
    }

}
