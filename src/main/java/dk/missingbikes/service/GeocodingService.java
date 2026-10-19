package dk.missingbikes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

/**
 * Reverse geocoding: turns a pair of coordinates into a readable address, using
 * the Google Geocoding API.
 */
@Service
public class GeocodingService {

    private static final String GEOCODE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    private final RestClient restClient;

    @Value("${google.maps.api-key}")
    private String googleApiKey;

    public GeocodingService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Returns an empty string when Google knows no address for the point, which
     * is a normal answer for the middle of a field or the sea, not an error.
     */
    public String findAddress(double latitude, double longitude){
        String url = GEOCODE_URL + "?latlng=" + latitude + "," + longitude + "&key=" + googleApiKey;
        JsonNode json = restClient.get().uri(url).retrieve().body(JsonNode.class);
        if(json == null || json.path("results").isEmpty()) {
            return "";
        }
        return json.path("results").get(0).path("formatted_address").asString();
    }
}
