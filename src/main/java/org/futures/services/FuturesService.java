package org.futures.services;

import org.futures.CoffeeDatasetLoader;
import org.futures.model.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FuturesService {

    private final KieContainer kieContainer;

    public FuturesService() {
        KieServices ks = KieServices.Factory.get();
        this.kieContainer = ks.getKieClasspathContainer();

    }

    public List<MarketSignal> evaluateCommodity(String commodityName, int investmentHorizon, String investorProfile) {
        // 1️⃣ Cria nova sessão Drools
        KieSession ksession = kieContainer.newKieSession("ksession-rules");

        // 2️⃣ Carrega dataset base (estoques, clima, taxa de câmbio, etc.)
        CoffeeDatasetLoader.loadDataset(ksession);

        // 3️⃣ Insere fatos principais
        LocalDate today = LocalDate.now();
        int targetMonth = (today.getMonthValue() + investmentHorizon) % 12;

        ksession.insert(new Commodity(commodityName, investmentHorizon, targetMonth));
        ksession.insert(new Investor(investorProfile));

        // 4️⃣ Executa regras por agenda group
        ksession.getAgenda().getAgendaGroup("commodity-rules").setFocus();
        ksession.fireAllRules();

        if ("Arabica".equalsIgnoreCase(commodityName)) {
            ksession.getAgenda().getAgendaGroup("arabica").setFocus();
            ksession.fireAllRules();
        } else if ("Robusta".equalsIgnoreCase(commodityName)) {
            ksession.getAgenda().getAgendaGroup("robusta").setFocus();
            ksession.fireAllRules();
        }

        ksession.getAgenda().getAgendaGroup("conclusions").setFocus();
        ksession.fireAllRules();

        // 5️⃣ Captura resultados finais
        @SuppressWarnings("unchecked")
        List<MarketSignal> signals = ksession.getObjects(o -> o instanceof MarketSignal)
                .stream()
                .map(o -> (MarketSignal) o)
                .toList();


        // ✅ Print no console antes de retornar
        System.out.println("📊 --- Market Signals ---");
        if (signals == null || signals.isEmpty()) {
            System.out.println("⚠️ Nenhum sinal gerado pelo motor de regras.");
        } else {
            signals.forEach(signal ->
                    System.out.printf("➡️ Sentiment: %s | Confidence: %.2f%n",
                            signal.getDecision(), signal.getConfidence())
            );
        }
        System.out.println("--------------------------");

        ksession.dispose();

        return signals;
    }
}
