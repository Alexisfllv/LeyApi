package hub.com.leyapi.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    // Carpeta raíz donde se guardarán los archivos
    private final Path rootLocation = Paths.get("uploads");

    // Tamaño máximo permitido: 5 MB
    private static final long MAX_FILE_SIZE = 5_000_000;

    public String save(MultipartFile file) {
        try {
            // Validación: archivo no vacío
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Archivo no encontrado");
            }

            // Validación: solo PDFs
            if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
                throw new IllegalArgumentException("Solo se permiten archivos PDF");
            }

            // Validación: tamaño máximo
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException("Archivo demasiado grande. Máximo permitido: 5 MB");
            }
            // Generar nombre aleatorio con UUID + extensión .pdf
            String filename = UUID.randomUUID().toString() + ".pdf";

            // Guardar archivo en disco
            Path destino = rootLocation.resolve(filename);
            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return filename;

        } catch (Exception e) {
            throw new RuntimeException("Error guardando archivo", e);
        }
    }

    //
    public boolean exists(String filename) {
        String UPLOAD_DIR = "uploads";
        Path path = Paths.get(UPLOAD_DIR).resolve(filename); // UPLOAD_DIR = tu carpeta de uploads
        return Files.exists(path);
    }



}
