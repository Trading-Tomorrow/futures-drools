package org.futures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.futures.explain.DecisionExplanation;
import org.futures.model.*;
import org.futures.model.enums.CoffeeType;
import org.futures.model.enums.Horizon;
import org.futures.model.enums.InvestorProfile;
import org.kie.api.KieServices;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    static void main() throws Exception {

        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("ksession-rules");

        //Add event Listener
        var explainlistener = new org.futures.explain.ExplainListener();
        ksession.addEventListener((RuleRuntimeEventListener) explainlistener);
        ksession.addEventListener((AgendaEventListener) explainlistener);
        ksession.setGlobal("explain", explainlistener);


        // Add frontend inputs
        Inputs inputs = new Inputs(Horizon.SHORT, CoffeeType.ARABICA, InvestorProfile.BALANCED);
        ksession.insert(inputs);

        CoffeeDatasetLoader.loadDataset(ksession);

        LocalDate today = LocalDate.now();
        int investmentHorizon=4;
        String commodity ="Arabica";

        ksession.insert(new Commodity(commodity,investmentHorizon,(today.getMonthValue() + investmentHorizon)%12));
        ksession.insert(new Investor("Conservative"));


        // 3️⃣ Fire rules and get results
        ksession.getAgenda().getAgendaGroup("commodity-rules").setFocus();
        ksession.fireAllRules();
        if(commodity.equals("Arabica")){
            ksession.getAgenda().getAgendaGroup("arabica").setFocus();
            ksession.fireAllRules();
        }else if(commodity.equals("Robusta")){
            ksession.getAgenda().getAgendaGroup("robusta").setFocus();
            ksession.fireAllRules();
        }
        ksession.fireAllRules();

        ksession.getAgenda().getAgendaGroup("conclusions").setFocus();
        ksession.fireAllRules();

        DecisionExplanation de = explainlistener.build();

        System.out.println("\n=== 📜 HOW ===");
        de.getHow().forEach(System.out::println);

        System.out.println("\n=== ❓ WHY ===");
        de.getWhy().forEach(System.out::println);

        System.out.println("\n=== 🚫 WHY NOT ===");
        de.getWhyNot().forEach(System.out::println);

        ksession.dispose();

    }
}
