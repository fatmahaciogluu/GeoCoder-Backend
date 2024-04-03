package com.geocoder.GeoCoder.controller;

import com.geocoder.GeoCoder.model.Location;
import com.geocoder.GeoCoder.model.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class GeocoderController {

    @Value("${google.api.key}")
    private String apiKey;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/getLocation")
    public Response getGeoDetails(@RequestParam int radius, @RequestParam double lat, @RequestParam double lng){
        Location location = new Location();
        location.setLat(lat);
        location.setLng(lng);

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("maps/api/place/nearbysearch/json")
                .queryParam("key",apiKey)
                .queryParam("radius", radius)
                .queryParam("location", location.getLat() + "," + location.getLng())
                .build();
        System.out.println(uri.toUriString());
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        return response.getBody();
    }
}
