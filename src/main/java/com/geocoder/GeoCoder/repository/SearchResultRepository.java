package com.geocoder.GeoCoder.repository;

import com.geocoder.GeoCoder.entity.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Long> {

    Optional<SearchResult> findByRadiusAndLatitudeAndLongitude(double radius, double latitude, double longitude);

}