package dk.missingbikes.repository;

import dk.missingbikes.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
