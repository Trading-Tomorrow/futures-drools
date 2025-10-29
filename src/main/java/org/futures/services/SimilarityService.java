package org.futures.services;

import org.futures.model.EvaluationResult;
import org.futures.model.MarketSignal;

import java.util.*;
import java.util.stream.Collectors;

public class SimilarityService {

    /**
     * Compare the current evaluation with each month in backtesting.
     * It returns a sorted list of months whose signal similarity is above the given threshold.
     *
     * @param current     the current evaluation result
     * @param backtesting map of month -> EvaluationResult
     * @param threshold   minimum similarity (0–1) for inclusion
     * @return list of months ranked by similarity (highest first)
     */
    public List<SimilarityMatch> findSimilarMonths(
            EvaluationResult current,
            Map<Integer, EvaluationResult> backtesting,
            double threshold
    ) {
        if (backtesting == null || backtesting.isEmpty()) {
            return Collections.emptyList();
        }

        List<SimilarityMatch> results = new ArrayList<>();

        for (Map.Entry<Integer, EvaluationResult> entry : backtesting.entrySet()) {
            int month = entry.getKey();
            EvaluationResult past = entry.getValue();

            double similarity = calculateSignalSimilarity(current, past);

            if (similarity >= threshold) {
                results.add(new SimilarityMatch(month, similarity));
            }
        }


        results.sort(Comparator.comparingDouble(SimilarityMatch::similarity).reversed());
        return results;
    }

    private double calculateSignalSimilarity(EvaluationResult a, EvaluationResult b) {
        List<MarketSignal> sigA = a.getSignals();
        List<MarketSignal> sigB = b.getSignals();

        if (sigA.isEmpty() || sigB.isEmpty()) return 0.0;

        // Assume corresponding signals refer to same month / same index meaning
        int size = Math.min(sigA.size(), sigB.size());
        double totalScore = 0.0;

        for (int i = 0; i < size; i++) {
            MarketSignal sA = sigA.get(i);
            MarketSignal sB = sigB.get(i);

            if (sA.getDecision().equalsIgnoreCase(sB.getDecision())) {
                // decisions match → evaluate confidence closeness
                double diff = Math.abs(sA.getConfidence() - sB.getConfidence());
                double score = 1.0 - diff; // closer confidence = higher score
                if (score < 0) score = 0;  // safety clamp
                totalScore += score;

            } else {
                // decisions differ → this signal contributes 0 similarity
                totalScore += 0;
            }
        }

        return totalScore / size; // normalize to 0–1 similarity
    }

    /**
     * Simple DTO for returning the comparison results
     */
    public static record SimilarityMatch(int month, double similarity) { }
}
