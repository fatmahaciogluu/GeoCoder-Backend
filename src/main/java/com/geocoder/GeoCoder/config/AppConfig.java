package com.geocoder.GeoCoder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${google.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
