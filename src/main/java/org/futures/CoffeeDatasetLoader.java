package org.futures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.futures.model.*;
import org.kie.api.runtime.KieSession;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class CoffeeDatasetLoader {
    public static void loadDataset(KieSession ksession) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream input = CoffeeDatasetLoader.class
                .getClassLoader()
                .getResourceAsStream("dataset/dataset.json")) {

            if (input == null) {
                System.err.println("Dataset not found!");
                return;
            }

            Map<String, Object> root = mapper.readValue(input, new TypeReference<>() {});
            System.out.println("Loading dataset...\n\n");
            System.out.println("─────────────────────────────────────────────");

            List<Map<String, Object>> exchangeRates = (List<Map<String, Object>>) root.get("exchange_rate");
            for (var record : exchangeRates) {
                ExchangeRate fx = new ExchangeRate(
                        (String) record.get("country"),
                        ((Number) record.get("month")).intValue(),
                        ((Number) record.get("variation")).doubleValue()
                );
                ksession.insert(fx);
            }

            List<Map<String, Object>> productions = (List<Map<String, Object>>) root.get("production");
            for (var record : productions) {
                Production prod = new Production(
                        (String) record.get("country"),
                        (String) record.get("commodity"),
                        (String) record.get("region"),
                        ((Number) record.get("month")).intValue(),
                        ((Number) record.get("production")).doubleValue()
                );
                ksession.insert(prod);
            }

            List<Map<String, Object>> weathers = (List<Map<String, Object>>) root.get("weather");
            for (var record : weathers) {
                Weather weather = new Weather(
                        (String) record.get("country"),
                        (String) record.get("region"),
                        ((Number) record.get("month")).intValue(),
                        ((Number) record.get("temperature")).doubleValue(),
                        ((Number) record.get("spi")).doubleValue()
                );
                ksession.insert(weather);
            }

            List<Map<String, Object>> volumes = (List<Map<String, Object>>) root.get("volume");
            for (var record : volumes) {
                Volume vol = new Volume(
                        (String) record.get("commodity"),
                        ((Number) record.get("month")).intValue(),
                        ((Number) record.get("buy_volume")).doubleValue(),
                        ((Number) record.get("buy_volume_7d")).doubleValue(),
                        ((Number) record.get("open_interest")).doubleValue(),
                        ((Number) record.get("trade_size")).intValue()
                );
                ksession.insert(vol);
            }

            List<Map<String, Object>> perceptions = (List<Map<String, Object>>) root.get("market_perception");
            for (var record : perceptions) {
                MarketPerception per = new MarketPerception(
                        (String) record.get("commodity"),
                        (String) record.get("source"),
                        (String) record.get("perception"),
                        ((Number) record.get("intensity")).doubleValue(),
                        (String) record.get("description"),
                        ((Number) record.get("month")).intValue(),
                        (String) record.get("country")
                );
                ksession.insert(per);
            }

            System.out.println("─────────────────────────────────────────────");
            System.out.println("Dataset fully loaded");

        } catch (Exception e) {
            System.err.println("Error loading dataset: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
