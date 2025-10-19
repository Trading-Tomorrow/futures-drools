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
        ksession.dispose();

    }
}
