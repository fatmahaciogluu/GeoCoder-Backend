package com.geocoder.GeoCoder.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geocoder.GeoCoder.entity.SearchResult;
import com.geocoder.GeoCoder.model.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class GooglePlacesService {

    private final RestTemplate restTemplate;
    private final SearchResultService searchResultService;

    @Autowired
    public GooglePlacesService(RestTemplate restTemplate, SearchResultService searchResultService) {
        this.restTemplate = restTemplate;
        this.searchResultService = searchResultService;
    }

    @Transactional
    public ResponseData fetchAndSaveNearbyPlaces(String apiKey, double radius, double lat, double lng) {
        Optional<SearchResult> cachedResult = searchResultService.findCachedResult(radius, lat, lng);
        if (cachedResult.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                System.out.println("Data is from DB");
                return objectMapper.readValue(cachedResult.get().getJsonData(), ResponseData.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("maps/api/place/nearbysearch/json")
                .queryParam("key", apiKey)
                .queryParam("radius", radius)
                .queryParam("location", lat + "," + lng)
                .build();

        String url = uri.toUriString();
        String jsonResponse = restTemplate.getForObject(url, String.class);

        searchResultService.saveResult(radius, lat, lng, jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println("Data is from API");
            return objectMapper.readValue(jsonResponse, ResponseData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}

