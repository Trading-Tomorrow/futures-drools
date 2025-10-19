package org.futures.model;

import java.util.List;

public class EvaluationResult {
    private String commodity;
    private String investor;
    private List<MarketSignal> signals;
    private List<Hypothesis> hypotheses;

    public EvaluationResult(String commodity, String investor,
                            List<MarketSignal> signals,
                            List<Hypothesis> hypotheses) {
        this.commodity = commodity;
        this.investor = investor;
        this.signals = signals;
        this.hypotheses = hypotheses;
    }

    public String getCommodity() { return commodity; }
    public String getInvestor() { return investor; }
    public List<MarketSignal> getSignals() { return signals; }
    public List<Hypothesis> getHypotheses() { return hypotheses; }
}
