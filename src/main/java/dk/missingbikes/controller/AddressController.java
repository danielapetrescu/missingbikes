package dk.missingbikes.controller;

import dk.missingbikes.dto.AddressCoordinates;
import dk.missingbikes.dto.AddressSuggestion;
import dk.missingbikes.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/suggestions")
    public List<AddressSuggestion> getSuggestions(@RequestParam String query){
        return addressService.findSuggestions(query);
    }

    @GetMapping("/details")
    public AddressCoordinates getCoordinates(@RequestParam String placeId){
        return addressService.findCoordinates(placeId);
    }
}
