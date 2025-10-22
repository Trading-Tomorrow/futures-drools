package org.futures.model;

import org.futures.services.SimilarityService;
import java.util.List;
import java.util.Map;

public class FinalResponse {

    private final EvaluationResult evaluationResult;
    private final Map<Integer, EvaluationResult> backtesting;
    private final List<SimilarityService.SimilarityMatch> similarityMatches;

    // ✅ Full constructor (evaluation + backtesting + similarity)
    public FinalResponse(EvaluationResult evaluationResult,
                         Map<Integer, EvaluationResult> backtesting,
                         List<SimilarityService.SimilarityMatch> similarityMatches) {
        this.evaluationResult = evaluationResult;
        this.backtesting = backtesting;
        this.similarityMatches = similarityMatches;
    }

    // ✅ Overloaded: evaluation + backtesting only
    public FinalResponse(EvaluationResult evaluationResult,
                         Map<Integer, EvaluationResult> backtesting) {
        this(evaluationResult, backtesting, null);
    }

    // ✅ Overloaded: evaluation only
    public FinalResponse(EvaluationResult evaluationResult) {
        this(evaluationResult, null, null);
    }

    public EvaluationResult getEvaluationResult() {
        return evaluationResult;
    }

    public Map<Integer, EvaluationResult> getBacktesting() {
        return backtesting;
    }

    public List<SimilarityService.SimilarityMatch> getSimilarityMatches() {
        return similarityMatches;
    }
}
