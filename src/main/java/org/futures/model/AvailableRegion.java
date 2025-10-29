package org.futures.model;

public class AvailableRegion {
    private String country;
    private String region;

    public AvailableRegion(String country, String region) {
        this.country = country;
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }
}
