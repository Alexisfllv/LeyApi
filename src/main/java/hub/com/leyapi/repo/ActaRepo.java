package hub.com.leyapi.repo;

import hub.com.leyapi.model.Actas;
import hub.com.leyapi.model.Chives;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActaRepo extends JpaRepository<Actas, Long> {
}
