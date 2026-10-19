package dk.missingbikes.controller;

import dk.missingbikes.exception.InvalidInputException;
import dk.missingbikes.model.Color;
import dk.missingbikes.service.BikeService;
import dk.missingbikes.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PageController {

    private final BrandService brandService;
    private final BikeService bikeService;

    public PageController(BrandService brandService, BikeService bikeService) {
        this.brandService = brandService;
        this.bikeService = bikeService;
    }

    @GetMapping("/add")
    public String showAddBikeForm(Model model){
        setBrandAndColors(model);
        return "add-bike";
    }

    @GetMapping("/")
    public String showBikeList(Model model){
        model.addAttribute("bikes", bikeService.findAll());
        return "home";
    }

    @PostMapping("/add")
    public String addBike(@RequestParam Long brand,
                          @RequestParam Color color,
                          @RequestParam String description,
                          @RequestParam Double latitude,
                          @RequestParam Double longitude,
                          @RequestParam String address,
                          @RequestParam("photos") MultipartFile[] photos,
                          Model model){
        try {
            bikeService.addBike(brand, color, description, latitude, longitude, address, photos);
        } catch(InvalidInputException e){
            setBrandAndColors(model);
            model.addAttribute("error", e.getMessage());
            return "add-bike";
        }
        return "redirect:/";
    }

    private void setBrandAndColors(Model model){
        model.addAttribute("brands", brandService.findAllSortedByName());
        model.addAttribute("colors", Color.values());
    }
}
