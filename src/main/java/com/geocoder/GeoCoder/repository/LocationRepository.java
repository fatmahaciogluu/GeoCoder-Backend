package com.geocoder.GeoCoder.repository;

import com.geocoder.GeoCoder.model.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByRadiusAndLatAndLng(int radius, double lat, double lng);

}