package dk.missingbikes.dto;

import dk.missingbikes.model.Brand;

public record BrandResponse(Long id, String name) {
    public static BrandResponse fromBrand(Brand brand){
        return new BrandResponse(brand.getId(), brand.getName());
    }
}
