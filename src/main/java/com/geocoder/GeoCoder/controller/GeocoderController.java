package com.geocoder.GeoCoder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geocoder.GeoCoder.model.Response;
import com.geocoder.GeoCoder.model.Result;
import com.geocoder.GeoCoder.model.entity.LocationEntity;
import com.geocoder.GeoCoder.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
public class GeocoderController {

    @Autowired
    private LocationRepository locationRepository;

    @Value("${google.api.key}")
    private String apiKey;

    @GetMapping("/getLocation")
    public ResponseEntity<LocationEntity> getGeoDetails(@RequestParam int radius, @RequestParam double lat, @RequestParam double lng) {
        Optional<LocationEntity> existingLocation = locationRepository.findByRadiusAndLatAndLng(radius, lat, lng);

        if (existingLocation.isPresent()) {
            System.out.println("Data source is database");
            return ResponseEntity.ok(existingLocation.get());
        } else {
            ResponseEntity<Response> responseEntity = callPlacesApi(radius, lat, lng);

            if (responseEntity != null && responseEntity.getBody() != null) {
                Response response = responseEntity.getBody();
                LocationEntity locationEntity = createLocationEntity(response, radius, lat, lng);

                locationRepository.save(locationEntity);
                System.out.println("Data source is Google API");

                return ResponseEntity.ok(locationEntity);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    private ResponseEntity<Response> callPlacesApi(int radius, double lat, double lng) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("maps/api/place/nearbysearch/json")
                .queryParam("key", apiKey)
                .queryParam("radius", radius)
                .queryParam("location", lat + "," + lng)
                .build();

        System.out.println("API URL: " + uri.toUriString());
        return new RestTemplate().getForEntity(uri.toUriString(), Response.class);
    }

    private LocationEntity createLocationEntity(Response response, int radius, double lat, double lng) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setRadius(radius);
        locationEntity.setLat(lat);
        locationEntity.setLng(lng);

        if (response != null && response.getResult() != null) {
            Result[] results = response.getResult();
            String resultsJson = convertResultsToJson(results);
            locationEntity.setResults(resultsJson);
        }

        return locationEntity;
    }

    private String convertResultsToJson(Result[] results) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}