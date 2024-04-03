package com.geocoder.GeoCoder.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty("formatted_address")
    private String address;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    private Geometry geometry;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
