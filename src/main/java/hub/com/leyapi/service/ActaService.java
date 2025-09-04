package hub.com.leyapi.service;

import hub.com.leyapi.dto.actas.ActaDTOResponse;
import hub.com.leyapi.model.Chives;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActaService {

    ActaDTOResponse saveActa(String titulo, List<MultipartFile> docs);
    Resource downloadAllFiles(Long id);
}
