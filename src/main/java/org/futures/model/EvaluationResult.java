package org.futures.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EvaluationResult {
    private final String commodity;
    private final String investor;
    private final List<MarketSignal> signals;
    private final List<Hypothesis> hypotheses;
    private final Optional<Map<Integer, EvaluationResult>> backtesting;

    // ✅ Constructor for main results (with backtesting)
    public EvaluationResult(String commodity, String investor,
                            List<MarketSignal> signals,
                            List<Hypothesis> hypotheses,
                            Map<Integer, EvaluationResult> backtesting) {
        this.commodity = commodity;
        this.investor = investor;
        this.signals = signals;
        this.hypotheses = hypotheses;
        this.backtesting = Optional.ofNullable(backtesting);
    }

    // ✅ Overloaded constructor for cases without backtesting
    public EvaluationResult(String commodity, String investor,
                            List<MarketSignal> signals,
                            List<Hypothesis> hypotheses) {
        this(commodity, investor, signals, hypotheses, null);
    }

    public String getCommodity() {
        return commodity;
    }

    public String getInvestor() {
        return investor;
    }

    public List<MarketSignal> getSignals() {
        return signals;
    }

    public List<Hypothesis> getHypotheses() {
        return hypotheses;
    }

    public Optional<Map<Integer, EvaluationResult>> getBacktesting() {
        return backtesting;
    }
}
