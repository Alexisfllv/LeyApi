package hub.com.leyapi.service.impl;

import hub.com.leyapi.dto.actas.ActaDTOResponse;
import hub.com.leyapi.mapper.ActaMapper;
import hub.com.leyapi.mapper.ChiveMapper;
import hub.com.leyapi.model.Actas;
import hub.com.leyapi.model.Chives;
import hub.com.leyapi.repo.ActaRepo;
import hub.com.leyapi.repo.ChiveRepo;
import hub.com.leyapi.service.ActaService;
import hub.com.leyapi.util.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActaServiceImpl implements ActaService {

    private final ChiveMapper chiveMapper;
    private final ChiveRepo chiveRepo;
    private final StorageService storageService;
    private final ActaRepo actaRepo;
    private final ActaMapper actaMapper;


    @Transactional
    @Override
    public ActaDTOResponse saveActa(String titulo, List<MultipartFile> docs) {
        Actas acta = new Actas();
        acta.setTitulo(titulo);

        // guardar archivos
        for(MultipartFile file : docs) {
            String fileUrl = storageService.save(file);
            Chives chives = new Chives();
            chives.setNombre(file.getOriginalFilename());
            chives.setUrl(fileUrl);
            chives.setActa(acta);

            acta.getChives().add(chives);
        }
        Actas acte =  actaRepo.save(acta);
        log.warn(acte.toString());
        return actaMapper.toActaDTOResponse(acte);
    }

    @Override
    public Resource downloadAllFiles(Long id) {
        Actas acta = actaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Acta no encontrado"));

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            for(Chives chives : acta.getChives()) {
                Path filePath = Paths.get("uploads").resolve(chives.getUrl()).normalize();

                try (InputStream fis = Files.newInputStream(filePath)){
                    ZipEntry zipEntry = new ZipEntry(chives.getNombre());
                    zos.putNextEntry(zipEntry);

                    byte[] bytes = fis.readAllBytes();
                    zos.write(bytes,0, bytes.length);
                    zos.closeEntry();
                }
            }
            zos.close();
            return new ByteArrayResource(baos.toByteArray());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource downloadActa(Long idActa, Long idChives) {
        // Buscar directamente el chives con acta en la BD
        Chives chives = chiveRepo.findByIdChivesAndActa_IdActa(idChives, idActa)
                .orElseThrow(() -> new RuntimeException("Chive no encontrado para ese acta"));

        // Verificar existencia del archivo
        Path filePath = Paths.get("uploads")
                .resolve(chives.getUrl())
                .normalize();

        if (Files.notExists(filePath)) {
            throw new RuntimeException("Archivo físico no encontrado: " + filePath);
        }

        try {
            return new ByteArrayResource(Files.readAllBytes(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }
    }

    @Override
    public Page<ActaDTOResponse> findAllPageCategory(Pageable pageable) {
        Page<Actas> actas = actaRepo.findAll(pageable);
        return actas.map(actas1 ->  actaMapper.toActaDTOResponse(actas1));

    }

}
