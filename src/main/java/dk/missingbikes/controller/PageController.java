package dk.missingbikes.controller;

import dk.missingbikes.model.Color;
import dk.missingbikes.repository.BrandRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final BrandRepository brandRepository;

    public PageController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @GetMapping("/add")
    public String showAddBikeForm(Model model){
        model.addAttribute("brands", brandRepository.findAllByOrderByNameAsc());
        model.addAttribute("colors", Color.values());
        return "add-bike";
    }
}
