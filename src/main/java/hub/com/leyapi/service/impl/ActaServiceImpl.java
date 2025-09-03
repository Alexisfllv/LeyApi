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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public ActaDTOResponse getActaById(Long id) {
        return null;
    }
}
