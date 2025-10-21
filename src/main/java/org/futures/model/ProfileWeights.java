package org.futures.model;

/**
 * Facto simples que contém os pesos aplicáveis às decisões
 * de BUY, SELL e HOLD, de acordo com o perfil do investidor.
 */
public class ProfileWeights {

    private double buyW;
    private double sellW;
    private double holdW;

    public ProfileWeights() {}

    public ProfileWeights(double buyW, double sellW, double holdW) {
        this.buyW = buyW;
        this.sellW = sellW;
        this.holdW = holdW;
    }

    public double getBuyW() {
        return buyW;
    }

    public void setBuyW(double buyW) {
        this.buyW = buyW;
    }

    public double getSellW() {
        return sellW;
    }

    public void setSellW(double sellW) {
        this.sellW = sellW;
    }

    public double getHoldW() {
        return holdW;
    }

    public void setHoldW(double holdW) {
        this.holdW = holdW;
    }

    @Override
    public String toString() {
        return "ProfileWeights{" +
                "buyW=" + buyW +
                ", sellW=" + sellW +
                ", holdW=" + holdW +
                '}';
    }
}
