package dk.missingbikes.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@RestController
public class GeocodeController {

    @Value("${google.maps.api-key}")
    private String googleApiKey;

    @GetMapping("/api/geocode")
    public String getAddress(@RequestParam double latitude, @RequestParam double longitude){
        String url = "https://maps.googleapis.com/maps/api/geocode/json?"
                +"latlng="+ latitude + "," + longitude
                +"&key="+googleApiKey;
        RestClient restClient = RestClient.create();
        JsonNode json = restClient.get().uri(url).retrieve().body(JsonNode.class);
        String response = "";
        if(json!= null && !json.path("results").isEmpty()) {
            response = json.path("results").get(0).path("formatted_address").asString();
        }
        return response;
    }
}
