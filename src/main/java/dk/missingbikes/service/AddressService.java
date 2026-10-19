package dk.missingbikes.service;

import dk.missingbikes.dto.AddressCoordinates;
import dk.missingbikes.dto.AddressSuggestion;
import dk.missingbikes.exception.InvalidInputException;
import dk.missingbikes.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Talks to the Google Places API: address autocomplete and the coordinates of a
 * chosen place. Knows nothing about HTTP requests coming into this application.
 */
@Service
public class AddressService {

    private static final String AUTOCOMPLETE_URL = "https://places.googleapis.com/v1/places:autocomplete";
    private static final String DETAILS_URL = "https://places.googleapis.com/v1/places/";

    private final RestClient restClient;

    @Value("${google.places.api-key}")
    private String placeKey;

    public AddressService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<AddressSuggestion> findSuggestions(String query){
        if(query.isBlank()){
            return List.of();
        }
        Map<String, Object> bodyMap = Map.of("input", query, "includedRegionCodes", List.of("dk"));
        JsonNode json = restClient.post().uri(AUTOCOMPLETE_URL)
                .header("X-Goog-Api-Key", placeKey)
                .body(bodyMap)
                .retrieve().body(JsonNode.class);

        List<AddressSuggestion> suggestions = new ArrayList<>();
        if(json != null) {
            for (JsonNode node : json.path("suggestions")) {
                JsonNode suggestionNode = node.path("placePrediction");
                // Google also returns queryPrediction entries, which carry no placeId
                // and cannot be turned into coordinates later.
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

    public AddressCoordinates findCoordinates(String placeId){
        if(placeId.isBlank()){
            throw new InvalidInputException("placeId is required");
        }
        JsonNode json = restClient.get().uri(DETAILS_URL + placeId)
                .header("X-Goog-Api-Key", placeKey)
                .header("X-Goog-FieldMask", "location")
                .retrieve().body(JsonNode.class);

        // The null check comes first: reading a path out of a null body would
        // fail with a NullPointerException before this check could run.
        if (json == null) {
            throw new NotFoundException("No coordinates found for this place");
        }
        JsonNode location = json.path("location");
        if (location.isMissingNode()) {
            throw new NotFoundException("No coordinates found for this place");
        }

        Double latitude = location.path("latitude").asDouble();
        Double longitude = location.path("longitude").asDouble();
        return new AddressCoordinates(latitude, longitude);
    }
}
