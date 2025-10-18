package org.futures;

import org.futures.model.*;
import org.kie.api.KieServices;
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

        CoffeeDatasetLoader.loadDataset(ksession);

        LocalDate today = LocalDate.now();
        int investmentHorizon=3;

        ksession.insert(new Commodity("Arábica",investmentHorizon,today.getMonthValue() + investmentHorizon));
        ksession.insert(new Investor("Conservative"));


        // 3️⃣ Fire rules and get results
        ksession.fireAllRules();
        ksession.dispose();

    }
}
