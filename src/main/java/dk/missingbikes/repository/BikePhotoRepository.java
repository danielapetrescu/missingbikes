package dk.missingbikes.repository;

import dk.missingbikes.model.BikePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikePhotoRepository extends JpaRepository<BikePhoto, Long> {
}
