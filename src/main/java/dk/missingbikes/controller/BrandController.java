package dk.missingbikes.controller;

import dk.missingbikes.dto.BrandRequest;
import dk.missingbikes.dto.BrandResponse;
import dk.missingbikes.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<BrandResponse> getAllBrands(){
        return brandService.findAll();
    }

    @PostMapping
    public BrandResponse addBrand(@RequestBody BrandRequest brand){
        return brandService.addBrand(brand);
    }
}
