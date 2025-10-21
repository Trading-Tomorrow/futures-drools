package org.futures.model;

public class Production {
    private String country;
    private String commodity;
    private String region;
    private int month;
    private double production;

    public Production(String country,  String commodity, String region,int month, double production) {
        this.country = country;
        this.month = month;
        this.commodity = commodity;
        this.region = region;
        this.production = production;
    }

    public String getCountry() {
        return country;
    }

    public int getMonth() {
        return month;
    }

    public String getCommodity() {
        return commodity;
    }

    public double getProduction() {
        return production;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return String.format("Production[%s-%s-%s: month=%d, prod=%.2f]", country, commodity, region, month, production);
    }
}
