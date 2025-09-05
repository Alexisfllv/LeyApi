package hub.com.leyapi.service;

import hub.com.leyapi.dto.actas.ActaDTOResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActaService {

    // Guardar Acta
    ActaDTOResponse saveActa(String titulo, List<MultipartFile> docs);

    // Descargar Documentos en zip de IdActa
    Resource downloadAllFiles(Long id);

    // Descargar DocumentId , por IdActa
    Resource downloadActa(Long idActa, Long idChives);

    // Listado Paginado
    Page<ActaDTOResponse> findAllPageCategory(Pageable pageable);



}
