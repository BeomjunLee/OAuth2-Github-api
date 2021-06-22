package security5.oauth2.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security5.oauth2.github.domain.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
