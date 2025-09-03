package hub.com.leyapi.dto.actas;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ActaDTORequest(
   String titulo,
   List<MultipartFile> chives

) {}
