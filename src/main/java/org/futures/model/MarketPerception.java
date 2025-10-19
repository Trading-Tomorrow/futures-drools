package org.futures.model;

public class MarketPerception {
    private String commodity;
    private String source;
    private String perception;
    private double intensity;

    public MarketPerception(String commodity, String source, String perception, double intensity) {
        this.commodity = commodity;
        this.source = source;
        this.perception = perception;
        this.intensity = intensity;
    }

    public String getCommodity() {
        return commodity;
    }

    public double getIntensity() {
        return intensity;
    }

    public String getPerception() {
        return perception;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return String.format("Market Perception %s %s %s %f",commodity,source,perception,intensity);
    }
}
