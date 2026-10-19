package dk.missingbikes.repository;

import dk.missingbikes.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findAllByOrderByNameAsc();
    boolean existsByNameIgnoreCase(String name);
}
