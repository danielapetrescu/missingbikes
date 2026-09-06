package dk.missingbikes.controller;

import dk.missingbikes.model.Bike;
import dk.missingbikes.repository.BikeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {
    private final BikeRepository bikeRepository;

    public BikeController(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @GetMapping
    public List<Bike> getAllBikes(){
        return bikeRepository.findAll();
    }

    @PostMapping
    public Bike addBike(@RequestBody Bike bike){
        return bikeRepository.save(bike);
    }
}
