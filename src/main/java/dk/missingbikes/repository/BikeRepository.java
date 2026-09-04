package dk.missingbikes.repository;

import dk.missingbikes.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
