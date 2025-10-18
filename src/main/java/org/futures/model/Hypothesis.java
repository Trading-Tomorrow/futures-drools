package org.futures.model;

import java.util.Map;

public class Hypothesis {
    private String commodity;
    private String condition;
    private Map<String,Object> factors;
    private double confidence;
    private String partialDecision;

    public Hypothesis(String commodity, String condition, Map<String,Object> factors, double confidence, String partialDecision) {
        this.commodity = commodity;
        this.condition = condition;
        this.factors = factors;
        this.confidence = confidence;
        this.partialDecision = partialDecision;
    }

    public String getCommodity() {
        return commodity;
    }

    public double getConfidence() {
        return confidence;
    }

    public Map<String, Object> getFactors() {
        return factors;
    }

    public String getCondition() {
        return condition;
    }

    public String getPartialDecision() {
        return partialDecision;
    }

    @Override
    public String toString() {
        return String.format("Hypothesis %s (%s %f) %s", condition,commodity, confidence,partialDecision);
    }
}
