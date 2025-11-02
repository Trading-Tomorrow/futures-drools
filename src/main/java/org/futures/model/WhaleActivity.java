package org.futures.model;

public class WhaleActivity {
    private String period;           
    private double volume;           
    private double avgTradeSize;     
    private double openInterestChange;
    private double volume7dAvg;      
    private double openInterest7dAvg;

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
