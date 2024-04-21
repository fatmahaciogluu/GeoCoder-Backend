package com.geocoder.GeoCoder.service;


import com.geocoder.GeoCoder.entity.SearchResult;
import com.geocoder.GeoCoder.repository.SearchResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchResultService {

    private final SearchResultRepository searchResultRepository;

    @Autowired
    public SearchResultService(SearchResultRepository searchResultRepository) {
        this.searchResultRepository = searchResultRepository;
    }

    public Optional<SearchResult> findCachedResult(double radius, double latitude, double longitude) {
        return searchResultRepository.findByRadiusAndLatitudeAndLongitude(radius, latitude, longitude);
    }

    public void saveResult(double radius, double latitude, double longitude, String jsonData) {
        SearchResult searchResult = new SearchResult();
        searchResult.setRadius(radius);
        searchResult.setLatitude(latitude);
        searchResult.setLongitude(longitude);
        searchResult.setJsonData(jsonData);

        searchResultRepository.save(searchResult);
    }
}
