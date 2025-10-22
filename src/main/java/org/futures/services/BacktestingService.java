package org.futures.services;

import org.futures.CoffeeDatasetLoader;
import org.futures.model.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.*;
import java.util.stream.Collectors;

/**
 * So this service will run together will our application to already apply the backtesting
 * with it we can cache the results.
 */
public class BacktestingService {

    private final KieContainer kieContainer;
    private final Map<Integer, EvaluationResult> backtestResults = new LinkedHashMap<>();

    public BacktestingService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    /**
     * Runs a full backtest for every month in the dataset,
     * creating a fresh Drools session each time and collecting
     * results (signals + hypotheses) in a map.
     */
    public Map<Integer, EvaluationResult> runBacktest(String commodityName, String investorProfile) {

        // Loop through 12 months (1–12)
        for (int month = 1; month <= 12; month++) {
            KieSession ksession = kieContainer.newKieSession("ksession-rules");

            // Load facts for all datasets (production, weather, volume, etc.)
            CoffeeDatasetLoader.loadDataset(ksession);

            // Commodity and investor setup
            int investmentHorizon = 0; // backtest: horizon = 0 (evaluate actual month)
            ksession.insert(new Commodity(commodityName, investmentHorizon, month));
            ksession.insert(new Investor(investorProfile));

            // Fire rules in structured order
            ksession.getAgenda().getAgendaGroup("commodity-rules").setFocus();
            ksession.fireAllRules();

            if ("Arabica".equalsIgnoreCase(commodityName)) {
                ksession.getAgenda().getAgendaGroup("arabica").setFocus();
                ksession.fireAllRules();
            } else if ("Robusta".equalsIgnoreCase(commodityName)) {
                ksession.getAgenda().getAgendaGroup("robusta").setFocus();
                ksession.fireAllRules();
            }

            ksession.getAgenda().getAgendaGroup("profile-weights").setFocus();
            ksession.fireAllRules();

            ksession.getAgenda().getAgendaGroup("conclusions").setFocus();
            ksession.fireAllRules();

            // Collect results
            List<MarketSignal> signals = ksession.getObjects(o -> o instanceof MarketSignal)
                    .stream()
                    .map(o -> (MarketSignal) o)
                    .collect(Collectors.toList());

            List<Hypothesis> hypotheses = ksession.getObjects(o -> o instanceof Hypothesis)
                    .stream()
                    .map(o -> (Hypothesis) o)
                    .collect(Collectors.toList());

            // Store monthly result
            EvaluationResult result = new EvaluationResult(commodityName, investorProfile, signals, hypotheses);
            this.backtestResults.put(month, result);

            // Optional: console summary
            System.out.printf("📅 Month %d — %d signals | %d hypotheses%n",
                    month, signals.size(), hypotheses.size());
            signals.forEach(s ->
                    System.out.printf("➡️  %s (%.2f)%n",  s.getDecision(), s.getConfidence())
            );

            // Dispose the session before next loop
            ksession.dispose();
        }

        return this.backtestResults;
    }

    /**
     * Utility: runs the backtest and prints a simple aggregate view
     */
    public void printSummary(String commodityName, String investorProfile) {
        Map<Integer, EvaluationResult> results = runBacktest(commodityName, investorProfile);

        System.out.println("\n===== 🧠 Backtest Summary =====");
        results.forEach((month, eval) -> {
            System.out.printf("📆 M%02d → ", month);
            eval.getSignals().forEach(sig ->
                    System.out.printf("%s(%.2f) ", sig.getDecision(), sig.getConfidence())
            );
            System.out.println();
        });
        System.out.println("===============================");
    }
}
