package dk.missingbikes.service;

import dk.missingbikes.model.Bike;
import dk.missingbikes.model.BikePhoto;
import dk.missingbikes.model.Brand;
import dk.missingbikes.model.Color;
import dk.missingbikes.repository.BikePhotoRepository;
import dk.missingbikes.repository.BikeRepository;
import dk.missingbikes.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Value;
import dk.missingbikes.exception.InvalidInputException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class BikeService {
    private final BikeRepository bikeRepository;
    private final BikePhotoRepository bikePhotoRepository;
    private final BrandRepository brandRepository;

    @Value("${bike.photos.upload-dir}")
    private String uploadDir;

    public BikeService(BikeRepository bikeRepository,
                          BikePhotoRepository bikePhotoRepository,
                          BrandRepository brandRepository) {
        this.bikeRepository = bikeRepository;
        this.bikePhotoRepository = bikePhotoRepository;
        this.brandRepository = brandRepository;
    }

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    /**
     * Reads every bike for the list page, pulling the brand and the photos in the
     * same query so that rendering a card does not trigger one SELECT per bike.
     */
    public List<Bike> findAll(){
        return bikeRepository.findAllWithBrandAndPhotos();
    }

    @Transactional
    public Bike addBike(Long brand, Color color, String description, Double latitude,
                                Double longitude, String address, MultipartFile[] photos){

        if(photos.length == 0 ){
            throw new InvalidInputException("Bike should have at least one photo");
        }
        List<String> normalizedExtensions = new ArrayList<>();
        for(MultipartFile photo: photos ) {
            String extension = StringUtils.getFilenameExtension(photo.getOriginalFilename());
            if (extension == null) {
                throw new InvalidInputException("The photo has no file extension");
            }
            String normalizedExtension = extension.toLowerCase(Locale.ROOT);
            if (!ALLOWED_EXTENSIONS.contains(normalizedExtension)) {
                throw new InvalidInputException("The photo's extension is not permitted");
            }
            normalizedExtensions.add(normalizedExtension);
        }
        Brand bikeBrand = brandRepository.findById(brand).orElseThrow(
                () -> new InvalidInputException("Brand is not correct"));

        Bike bike = new Bike();
        bike.setBrand(bikeBrand);
        bike.setLatitude(latitude);
        bike.setLongitude(longitude);
        bike.setAddress(address);
        bike.setColor(color);
        bike.setDate(LocalDate.now());
        if(!description.isEmpty()){
            bike.setDescription(description);
        }
        Bike newBike = bikeRepository.save(bike);

        Path path = Path.of(uploadDir);
        try {
            // One directory per bike, so photo names can stay simple (1.jpg, 2.jpg)
            // without risking a collision between two bikes.
            String revPath = newBike.getId().toString();
            Path photoDirPath = path.resolve(revPath);
            Files.createDirectories(photoDirPath);
            int i = 1;
            for(MultipartFile photo: photos ){
                String extension = normalizedExtensions.get(i-1);
                String photoName = i++ + "." + extension;
                Path photoPath = photoDirPath.resolve(photoName);
                photo.transferTo(photoPath);

                BikePhoto bikePhoto = new BikePhoto();
                bikePhoto.setBike(newBike);
                // The database keeps only "<bike id>/<file name>", e.g. "1/1.jpg".
                // The upload directory is left out on purpose: it is a setting that can
                // change, and the page builds the image URL as "/photos/" + this value.
                // Forward slash, not backslash, because this string ends up in a URL.
                bikePhoto.setPath(revPath + "/" + photoName);
                bikePhotoRepository.save(bikePhoto);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return newBike;
    }
}
