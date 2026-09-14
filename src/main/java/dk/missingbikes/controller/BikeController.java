package dk.missingbikes.controller;

import dk.missingbikes.model.Bike;
import dk.missingbikes.model.BikePhoto;
import dk.missingbikes.model.Brand;
import dk.missingbikes.model.Color;
import dk.missingbikes.repository.BikePhotoRepository;
import dk.missingbikes.repository.BikeRepository;
import dk.missingbikes.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {

    private final BikeRepository bikeRepository;
    private final BikePhotoRepository bikePhotoRepository;
    private final BrandRepository brandRepository;

    @Value("${bike.photos.upload-dir}")
    private String uploadDir;

    public BikeController(BikeRepository bikeRepository,
                          BikePhotoRepository bikePhotoRepository,
                          BrandRepository brandRepository) {
        this.bikeRepository = bikeRepository;
        this.bikePhotoRepository = bikePhotoRepository;
        this.brandRepository = brandRepository;
    }

    @GetMapping
    public List<Bike> getAllBikes(){
        return bikeRepository.findAll();
    }

    @PostMapping
    @Transactional
    public Bike addBike(@RequestParam Long brand,
                        @RequestParam Color color,
                        @RequestParam String description,
                        @RequestParam Double latitude,
                        @RequestParam Double longitude,
                        @RequestParam String address,
                        @RequestParam("photos") MultipartFile[] photos){

        if(photos.length == 0 ){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bike should have at least one photo");
        }
        Brand bikeBrand = brandRepository.findById(brand).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brand is not correct"));

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
            //create one directory for each bike
            Path photoDirPath = path.resolve(newBike.getId().toString());
            Files.createDirectories(photoDirPath);
            int i = 1;
            for(MultipartFile photo: photos ){
                String extension = StringUtils.getFilenameExtension(photo.getOriginalFilename());
                Path photoPath = photoDirPath.resolve(i++ + "." + extension);
                photo.transferTo(photoPath);

                BikePhoto bikePhoto = new BikePhoto();
                bikePhoto.setBike(newBike);
                bikePhoto.setPath(photoPath.toString());
                bikePhotoRepository.save(bikePhoto);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return newBike;
    }
}
