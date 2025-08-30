package hub.com.leyapi.repo;

import hub.com.leyapi.model.Documento;
import hub.com.leyapi.nums.DocumentoEstado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepo extends JpaRepository<Documento, Long> {

    // listar por documentos por el id usuario
    List<Documento> findByUsuarioIdUsuario(Long idUsuario);

    // buscar docuemnto por numero de expediente
    Optional<Documento> findByNumeroExpediente(String numeroExpediente);

    // buscar documento por estado
    List<Documento> findByEstado(DocumentoEstado estado);

}
