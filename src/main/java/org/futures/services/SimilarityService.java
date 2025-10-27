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

    /**
     * Calculates similarity between two sets of MarketSignals using
     * decision equality and confidence closeness.
     */
    private double calculateSignalSimilarity(EvaluationResult a, EvaluationResult b) {
        List<MarketSignal> sigA = a.getSignals();
        List<MarketSignal> sigB = b.getSignals();

        if (sigA.isEmpty() || sigB.isEmpty()) return 0.0;


        Map<String, Double> mapA = sigA.stream()
                .collect(Collectors.toMap(MarketSignal::getDecision, MarketSignal::getConfidence, (x, y) -> (x + y) / 2));

        Map<String, Double> mapB = sigB.stream()
                .collect(Collectors.toMap(MarketSignal::getDecision, MarketSignal::getConfidence, (x, y) -> (x + y) / 2));

        Set<String> allDecisions = new HashSet<>();
        allDecisions.addAll(mapA.keySet());
        allDecisions.addAll(mapB.keySet());

        double totalSim = 0.0;
        for (String decision : allDecisions) {
            double confA = mapA.getOrDefault(decision, 0.0);
            double confB = mapB.getOrDefault(decision, 0.0);


            double score = 1 - Math.abs(confA - confB);
            if (score < 0) score = 0;
            totalSim += score;
        }

        return totalSim / allDecisions.size();
    }

    /**
     * Simple DTO for returning the comparison results
     */
    public static record SimilarityMatch(int month, double similarity) { }
}
