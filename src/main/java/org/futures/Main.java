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
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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

        ksession.insert(new Weather("Brazil", "drought", 3.5, -10, 7));
        ksession.insert(new Sentiment("Bloomberg", "positive", 0.8, 7));
        ksession.insert(new Sentiment("Twitter", "negative", 0.7, 7));
        ksession.insert(new WhaleActivity("2025-07", 12000, 250, 8, 5000, 2));
        ksession.insert(new ExchangeRate("USDBRL", 5.35, 2.1, 1.8, 7));
        ksession.insert(new StockDemand("Brazil", 800000, 1000000, 3.5, 2.8, 7));

        // 3️⃣ Fire rules and get results
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
