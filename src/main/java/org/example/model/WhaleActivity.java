package org.example.model;

public class WhaleActivity {
    private String period;           // e.g., "2025-07"
    private double volume;           // total contracts traded
    private double avgTradeSize;     // average size per trade
    private double openInterestChange; // % change vs previous day/week
    private double volume7dAvg;      // 7-day moving average
    private double openInterest7dAvg; // 7-day OI avg for context

    public WhaleActivity(String period, double volume, double avgTradeSize, double openInterestChange, double volume7dAvg, double openInterest7dAvg) {
        this.period = period;
        this.volume = volume;
        this.avgTradeSize = avgTradeSize;
        this.openInterestChange = openInterestChange;
        this.volume7dAvg = volume7dAvg;
        this.openInterest7dAvg = openInterest7dAvg;
    }

    public String getPeriod() { return period; }
    public double getVolume() { return volume; }
    public double getAvgTradeSize() { return avgTradeSize; }
    public double getOpenInterestChange() { return openInterestChange; }
    public double getVolume7dAvg() { return volume7dAvg; }
    public double getOpenInterest7dAvg() { return openInterest7dAvg; }
}
