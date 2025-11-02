package org.futures.model;

import java.util.Map;

public class MarketSignal {
    private String month;      
    private String decision;   
    private double confidence;  
    private Map<String, Double> whyWeights;

    public MarketSignal(String month, String decision, double confidence, double sell, double buy, double hold) {
        this.month = month;
        this.decision = decision;
        this.confidence = confidence;
        this.whyWeights = Map.of(
                "sell", sell,
                "buy", buy,
                "hold", hold
        );
    }

    // 🔧 Overload for backward compatibility
    public MarketSignal(String decision, double confidence, double sell, double buy, double hold) {
        this("CURRENT", decision, confidence, sell, buy, hold);
    }

    public String getMonth() { return month; }
    public String getDecision() { return decision; }
    public double getConfidence() { return confidence; }

    public void setMonth(String month) { this.month = month; }
    public void setDecision(String decision) { this.decision = decision; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    public Map<String, Double> getWhyWeights() { return whyWeights; }
    public void setWhyWeights(Map<String, Double> whyWeights) { this.whyWeights = whyWeights; }

    @Override
    public String toString() {
        return String.format("MarketSignal{month='%s', decision='%s', confidence=%.2f}", month, decision, confidence);
    }
}
