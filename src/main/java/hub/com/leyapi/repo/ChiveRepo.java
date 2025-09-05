package hub.com.leyapi.repo;

import hub.com.leyapi.model.Chives;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChiveRepo extends JpaRepository<Chives, Long> {
    Optional<Chives> findByIdChivesAndActa_IdActa(Long idChives, Long idActa);

}
