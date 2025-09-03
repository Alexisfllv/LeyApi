package hub.com.leyapi.repo;

import hub.com.leyapi.model.Chives;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChiveRepo extends JpaRepository<Chives, Long> {
}
