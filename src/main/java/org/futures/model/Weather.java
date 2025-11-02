package org.futures.model;

public class Weather {
    private String country;
    private String region;
    private int month; 
    private double temperature; 
    private double spi; 

    public Weather(String country, String region, int month, double temperature, double spi) {
        this.country = country;
        this.region = region;
        this.month = month;
        this.temperature = temperature;
        this.spi = spi;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public int getMonth() {
        return month;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getSpi() {
        return spi;
    }

    @Override
    public String toString() {
        return String.format("Weather[%s-%s-%s: temp=%.1f°C, SPI=%.2f]", country, region, month, temperature, spi);
    }
}
