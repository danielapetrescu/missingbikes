package dk.missingbikes.controller;

import dk.missingbikes.dto.AddressCoordinates;
import dk.missingbikes.dto.AddressSuggestion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Value("${google.places.api-key}")
    private String placeKey;

    @GetMapping("/suggestions")
    public List<AddressSuggestion> getSuggestions(@RequestParam String query){
        if(query.isBlank()){
            return List.of();
        }
        String url = "https://places.googleapis.com/v1/places:autocomplete";
        Map<String, Object> bodyMap = Map.of("input", query, "includedRegionCodes", List.of("dk"));
        RestClient restClient = RestClient.create();
        JsonNode json = restClient.post().uri(url)
                .header("X-Goog-Api-Key", placeKey)
                .body(bodyMap)
                .retrieve().body(JsonNode.class);

        List<AddressSuggestion> suggestions = new ArrayList<>();
        if(json!= null) {
            for (JsonNode node : json.path("suggestions")) {
                JsonNode suggestionNode = node.path("placePrediction");
                if (suggestionNode.isMissingNode()) {
                    continue;
                }
                String placeId = suggestionNode.path("placeId").asString();
                String text = suggestionNode.path("text").path("text").asString();
                suggestions.add(new AddressSuggestion(placeId, text));
            }
        }
        return suggestions;
    }

    @GetMapping("/details")
    public AddressCoordinates getCoordinates(@RequestParam String placeId){
        if(placeId.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "placeId is required") ;
        }
        String url = "https://places.googleapis.com/v1/places/"+placeId;
        RestClient restClient = RestClient.create();
        JsonNode json = restClient.get().uri(url)
                .header("X-Goog-Api-Key", placeKey)
                .header("X-Goog-FieldMask", "location")
                .retrieve().body(JsonNode.class);

        JsonNode location = json.path("location");
        if (json == null || location.isMissingNode()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No coordinates found for this place");
        }

        Double latitude = location.path("latitude").asDouble();
        Double longitude = location.path("longitude").asDouble();
        return new AddressCoordinates(latitude, longitude);
    }
}
