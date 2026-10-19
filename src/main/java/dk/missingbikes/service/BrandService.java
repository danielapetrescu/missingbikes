package dk.missingbikes.service;

import dk.missingbikes.dto.BrandRequest;
import dk.missingbikes.dto.BrandResponse;
import dk.missingbikes.exception.InvalidInputException;
import dk.missingbikes.model.Brand;
import dk.missingbikes.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Business rules around bike brands. The uniqueness rule lives here rather than
 * in the controller, so both the API and the Add Bike page enforce it the same way.
 */
@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandResponse> findAll(){
        return brandRepository.findAll().stream()
                .map(BrandResponse::fromBrand)
                .collect(Collectors.toList());
    }

    public List<BrandResponse> findAllSortedByName(){
        return brandRepository.findAllByOrderByNameAsc().stream()
                .map(BrandResponse::fromBrand)
                .collect(Collectors.toList());
    }

    public BrandResponse addBrand(BrandRequest brand){
        if(!StringUtils.hasText(brand.name())){
            throw new InvalidInputException("Brand is empty");
        }
        String newName = StringUtils.capitalize(brand.name().trim());

        Brand newBrand = new Brand(newName);
        if(brandRepository.existsByNameIgnoreCase(newName)){
            throw new InvalidInputException("This brand already exists");
        }
        Brand savedBrand = brandRepository.save(newBrand);
        return BrandResponse.fromBrand(savedBrand);
    }
}
