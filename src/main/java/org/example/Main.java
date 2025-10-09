package org.example;

import org.example.model.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("ksession-rules");

        ksession.insert("Arabica");
        ksession.insert(new Weather("Brazil", "drought", 3.5, -10, 7));
        ksession.insert(new Sentiment("Bloomberg", "positive", 0.8, 7));
        ksession.insert(new Sentiment("Twitter", "negative", 0.7, 7));
        ksession.insert(new WhaleActivity(
                "2025-07",
                12000,     // volume
                250,       // avg trade size
                8,         // OI change %
                5000,      // avg volume 7d
                2          // avg OI change 7d (contextual)
        ));
        ksession.insert(new ExchangeRate("USDBRL", 5.35, 2.1, 1.8, 7));
        ksession.insert(new StockDemand(
                "Brazil",   // region
                800_000,    // stockLevel
                1_000_000,  // avgStockLevel
                3.5,        // demandChange (%)
                2.8,        // exportChange (%)
                7           // month
        ));

        ksession.fireAllRules();
        ksession.dispose();
    }
}
