package com.geocoder.GeoCoder.controller;

import com.geocoder.GeoCoder.repository.SearchResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class NearbyPlacesControllerTest {

    @Mock
    private SearchResultRepository searchResultRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NearbyPlacesController nearbyPlacesController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetGeoDetails_LocationFoundInDatabase() {
        /*LocationEntity mockLocation = new LocationEntity();
        mockLocation.setRadius(1500);
        mockLocation.setLat(-33.8670522);
        mockLocation.setLng(151.1957362);

        when(locationRepository.findByRadiusAndLatAndLng(anyInt(), anyDouble(), anyDouble()))
                .thenReturn(Optional.of(mockLocation));

        ResponseEntity<LocationEntity> response = geocoderController.getGeoDetails(1500, -33.8670522, 151.1957362);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockLocation, response.getBody());

        verify(locationRepository, times(1)).findByRadiusAndLatAndLng(1500, -33.8670522, 151.1957362);
        verify(restTemplate, never()).getForEntity(anyString(), eq(ResponseModel.class));

*/
    }
}
