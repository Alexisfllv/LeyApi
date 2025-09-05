package hub.com.leyapi.controller;


import hub.com.leyapi.dto.actas.ActaDTORequest;
import hub.com.leyapi.dto.actas.ActaDTOResponse;
import hub.com.leyapi.service.ActaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // descargar resource por idActa, idChives
    @GetMapping("/download/{idActa}/{idChives}")
    public ResponseEntity<Resource>downloadActa(@PathVariable Long idActa, @PathVariable Long idChives) {
        Resource resource = actaService.downloadActa(idActa, idChives);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+idChives+"\"")
                .body(resource);
    }

    // listado de actas paginado
    @GetMapping("/page")
    public ResponseEntity<Page<ActaDTOResponse>> findAllPage(
            @PageableDefault (
                    page = 0,
                    size = 10,
                    sort = "idActa",
                    direction = Sort.Direction.ASC
            )Pageable pageable
    ) {
        Page<ActaDTOResponse> page = actaService.findAllPageCategory(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


}
