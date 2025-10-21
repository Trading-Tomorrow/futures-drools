package org.futures.model;


public class Volume {
    private String commodity;
    private int month;
    private double buyVolume;
    private double buyVolume7d;
    private double openInterest;
    private int tradeSize;

    public Volume(String commodity, int month, double buyVolume, double buyVolume7d, double openInterest, int tradeSize) {
        this.commodity = commodity;
        this.month = month;
        this.buyVolume = buyVolume;
        this.buyVolume7d = buyVolume7d;
        this.openInterest = openInterest;
        this.tradeSize = tradeSize;
    }

    public String getCommodity() { return commodity; }
    public int getMonth() { return month; }
    public double getBuyVolume() { return buyVolume; }
    public double getBuyVolume7d() { return buyVolume7d; }
    public double getOpenInterest() { return openInterest; }
    public int getTradeSize() { return tradeSize; }

    @Override
    public String toString() {
        return String.format("Volume[%s M%d: Vol=%.1f | 7d=%.1f | OI=%.1f | Trade=%d]",
                commodity, month, buyVolume, buyVolume7d, openInterest, tradeSize);
    }
}