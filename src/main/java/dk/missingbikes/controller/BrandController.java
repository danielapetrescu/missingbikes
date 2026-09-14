package dk.missingbikes.controller;

import dk.missingbikes.model.Brand;
import dk.missingbikes.repository.BrandRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandRepository brandRepository;

    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @GetMapping
    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    @PostMapping
    public Brand addBrand(@RequestBody Brand brand){
        if(brandRepository.existsByName(brand.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This brand already exists");
        } else {
            return brandRepository.save(brand);
        }
    }
}
