package org.futures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.futures.model.*;
import org.kie.api.KieServices;
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

        CoffeeDatasetLoader.loadDataset(ksession);
        ksession.fireAllRules();

    }
}
