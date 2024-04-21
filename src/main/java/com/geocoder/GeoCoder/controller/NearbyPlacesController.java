package com.geocoder.GeoCoder.controller;

import com.geocoder.GeoCoder.model.ResponseData;
import com.geocoder.GeoCoder.service.GooglePlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NearbyPlacesController {

    private final GooglePlacesService googlePlacesService;

    @Autowired
    public NearbyPlacesController(GooglePlacesService googlePlacesService) {
        this.googlePlacesService = googlePlacesService;
    }

    @GetMapping("/nearby-places")
    public ResponseData getNearbyPlaces(
            @RequestParam String apiKey,
            @RequestParam double radius,
            @RequestParam double lat,
            @RequestParam double lng
    ) {
        return googlePlacesService.fetchAndSaveNearbyPlaces(apiKey, radius, lat, lng);
    }
}