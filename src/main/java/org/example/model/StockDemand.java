package org.example.model;

public class StockDemand {
    private String region;         // e.g., "Brazil", "Global"
    private double stockLevel;     // current stock in tons
    private double avgStockLevel;  // average of last N months
    private double demandChange;   // % change in demand (vs previous period)
    private double exportChange;   // % change in exports
    private int month;

    public StockDemand(String region, double stockLevel, double avgStockLevel, double demandChange, double exportChange, int month) {
        this.region = region;
        this.stockLevel = stockLevel;
        this.avgStockLevel = avgStockLevel;
        this.demandChange = demandChange;
        this.exportChange = exportChange;
        this.month = month;
    }

    public String getRegion() { return region; }
    public double getStockLevel() { return stockLevel; }
    public double getAvgStockLevel() { return avgStockLevel; }
    public double getDemandChange() { return demandChange; }
    public double getExportChange() { return exportChange; }
    public int getMonth() { return month; }
}
