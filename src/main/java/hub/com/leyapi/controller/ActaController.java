package hub.com.leyapi.controller;


import hub.com.leyapi.dto.actas.ActaDTORequest;
import hub.com.leyapi.dto.actas.ActaDTOResponse;
import hub.com.leyapi.service.ActaService;
import lombok.RequiredArgsConstructor;
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
}
