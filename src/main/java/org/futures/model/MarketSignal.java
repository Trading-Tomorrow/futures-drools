package org.futures.model;

public class MarketSignal {
    private String month;       // e.g., "2025-07"
    private String decision;    // "BUY", "HOLD", "SELL"
    private double confidence;  // 0.0–1.0 weight of signal

    public MarketSignal(String month, String decision, double confidence) {
        this.month = month;
        this.decision = decision;
        this.confidence = confidence;
    }

    // 🔧 Overload for backward compatibility
    public MarketSignal(String decision, double confidence) {
        this("CURRENT", decision, confidence);
    }

    public String getMonth() { return month; }
    public String getDecision() { return decision; }
    public double getConfidence() { return confidence; }

    public void setMonth(String month) { this.month = month; }
    public void setDecision(String decision) { this.decision = decision; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    @Override
    public String toString() {
        return String.format("MarketSignal{month='%s', decision='%s', confidence=%.2f}", month, decision, confidence);
    }
}
