package org.futures.services;

import org.futures.CoffeeDatasetLoader;
import org.futures.model.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class FuturesService {

    private final KieContainer kieContainer;
    private final BacktestingService backtestingSerivce;
    private final SimilarityService similarityService;

    public FuturesService() {
        KieServices ks = KieServices.Factory.get();
        this.kieContainer = ks.getKieClasspathContainer();
        this.backtestingSerivce = new BacktestingService(kieContainer);
        this.similarityService = new SimilarityService();
    }

    public FinalResponse evaluateCommodity(String commodityName, int investmentHorizon, String investorProfile) {

        Map<Integer, EvaluationResult> backtestingResults = this.backtestingSerivce.runBacktest(commodityName, investorProfile);


        KieSession ksession = kieContainer.newKieSession("ksession-rules");


        CoffeeDatasetLoader.loadDataset(ksession);


        LocalDate today = LocalDate.now();
        int targetMonth = ((today.getMonthValue() + investmentHorizon - 1) % 12) + 1;

        ksession.insert(new Commodity(commodityName, investmentHorizon, targetMonth));
        ksession.insert(new Investor(investorProfile));

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


        List<Hypothesis> hypotheses = ksession.getObjects(o -> o instanceof Hypothesis)
                .stream()
                .map(o -> (Hypothesis) o)
                .toList();

        List<MarketSignal> signals = ksession.getObjects(o -> o instanceof MarketSignal)
                .stream()
                .map(o -> (MarketSignal) o)
                .toList();




        System.out.println("--- Market Signals ---");
        if (signals == null || signals.isEmpty()) {
            System.out.println("Nenhum sinal gerado pelo motor de regras.");
        } else {
            signals.forEach(signal ->
                    System.out.printf("Sentiment: %s | Confidence: %.2f%n",
                            signal.getDecision(), signal.getConfidence())
            );
        }
        System.out.println("--------------------------");

        ksession.dispose();

        EvaluationResult evaluationResult = new EvaluationResult(commodityName, investorProfile, signals, hypotheses);
        List<SimilarityService.SimilarityMatch> similarMonths = this.similarityService.findSimilarMonths(evaluationResult, backtestingResults, 0.4);

        return new FinalResponse(evaluationResult,  backtestingResults, similarMonths);
    }
    public static boolean debugLhsValue(Object value) {
        System.out.println(value);
        return true;
    }
}
