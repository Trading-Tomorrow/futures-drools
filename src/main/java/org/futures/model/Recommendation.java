package org.futures.model;

public class Recommendation {

    public enum Action { BUY, SELL, HOLD }

    private final Action action;     // final decision
    private final double confidence; // 0..1
    private final String notes;      // justification

    public Recommendation(Action action, double confidence, String notes) {
        this.action = action;
        this.confidence = confidence;
        this.notes = notes;
    }

    public Action getAction()     { return action; }
    public double getConfidence() { return confidence; }
    public String getNotes()      { return notes; }

    @Override public String toString() {
        return "Recommendation{action=" + action +
                ", confidence=" + String.format("%.2f", confidence) +
                ", notes='" + notes + "'}";
    }
}
