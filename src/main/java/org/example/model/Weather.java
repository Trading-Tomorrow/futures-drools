package org.example.model;

public class Weather {
    private String region;
    private String condition; // e.g., "drought", "rain", "frost", "ideal"
    private double temperatureDeviation; // from normal, in °C
    private double precipitationDeviation; // percentage from normal
    private int month; // 1-12

    public Weather(String region, String condition, double temperatureDeviation, double precipitationDeviation, int month) {
        this.region = region;
        this.condition = condition;
        this.temperatureDeviation = temperatureDeviation;
        this.precipitationDeviation = precipitationDeviation;
        this.month = month;
    }

    public String getRegion() { return region; }
    public String getCondition() { return condition; }
    public double getTemperatureDeviation() { return temperatureDeviation; }
    public double getPrecipitationDeviation() { return precipitationDeviation; }
    public int getMonth() { return month; }
}
