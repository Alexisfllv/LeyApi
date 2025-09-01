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

    private final Path rootLocation = Paths.get("uploads");

    public String save(MultipartFile file) {
        try {
            if (file.isEmpty()) throw new RuntimeException("Archivo no encontrado");

            Files.createDirectories(rootLocation);

            String filename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            Path destino = rootLocation.resolve(filename);
            Files.copy(file.getInputStream(),destino, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Error guardando archivo",e);
        }
    }

    // recupeara nombre String de multipartfile


}
