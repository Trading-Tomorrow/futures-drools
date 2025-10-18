package org.futures.model;

public class ExchangeRate {
    private String country; // e.g., "USDBRL"
    private int month; // current exchange rate
    private double variation; // change compared to last week/day (%)

    public ExchangeRate(String country,  int month, double variation) {
        this.country = country;
        this.month = month;
        this.variation=variation;
    }

    public String getCountry() {
        return country;
    }

    public double getVariation() {
        return variation;
    }

    public int getMonth() { return month; }

    @Override
    public String toString() {
        return String.format("ExchangeRate[country=%s, month=%d, variation=%.2f%%]", country, month, variation);
    }
}
