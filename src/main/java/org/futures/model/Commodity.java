package org.futures.model;

public class Commodity {
    private String name;
    private int investmentHorizon;
    private int month;

    public Commodity(String name,int  investmentHorizon,int month) {
        this.name = name;
        this.investmentHorizon = investmentHorizon;
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public int getMonth() {
        return month;
    }

    public int getInvestmentHorizon() {
        return investmentHorizon;
    }
}
