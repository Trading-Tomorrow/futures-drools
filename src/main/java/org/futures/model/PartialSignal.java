package org.futures.model;

public class PartialSignal {
    private String category;   // "weather", "sentiment", "whale", "exchange", "stock"
    private String decision;   // "BUY", "SELL", "HOLD"
    private double confidence; // weight
    private String month;      // link to time

    public PartialSignal(String category, String decision, double confidence, String month) {
        this.category = category;
        this.decision = decision;
        this.confidence = confidence;
        this.month = month;
    }

    public String getCategory() { return category; }
    public String getDecision() { return decision; }
    public double getConfidence() { return confidence; }
    public String getMonth() { return month; }

    @Override
    public String toString() {
        return String.format("[%s → %s (%.2f)]", category, decision, confidence);
    }
}
