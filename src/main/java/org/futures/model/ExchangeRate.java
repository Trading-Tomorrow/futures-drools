package org.futures.model;

public class ExchangeRate {
    private String pair; // e.g., "USDBRL"
    private double currentRate; // current exchange rate
    private double changePercent; // change compared to last week/day (%)
    private double volatility; // standard deviation or recent volatility (%)
    private int month;

    public ExchangeRate(String pair, double currentRate, double changePercent, double volatility, int month) {
        this.pair = pair;
        this.currentRate = currentRate;
        this.changePercent = changePercent;
        this.volatility = volatility;
        this.month = month;
    }

    public String getPair() { return pair; }
    public double getCurrentRate() { return currentRate; }
    public double getChangePercent() { return changePercent; }
    public double getVolatility() { return volatility; }
    public int getMonth() { return month; }
}
