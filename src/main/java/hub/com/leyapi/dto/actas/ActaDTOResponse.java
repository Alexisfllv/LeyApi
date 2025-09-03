package hub.com.leyapi.dto.actas;

import hub.com.leyapi.dto.chive.ChiveDTOResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ActaDTOResponse(
        Long idActa,
        String titulo,
        List<ChiveDTOResponse> chives
) {
}
