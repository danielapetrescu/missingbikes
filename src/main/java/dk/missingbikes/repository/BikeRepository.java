package dk.missingbikes.repository;

import dk.missingbikes.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    @Query("select b from Bike b left join fetch b.photos join fetch b.brand order by b.date desc")
    List<Bike> findAllWithBrandAndPhotos();
}
