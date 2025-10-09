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
                .getResourceAsStream("dataset/coffee_futures_dataset.json")) {

            if (input == null) {
                System.err.println("❌ Dataset not found in resources folder!");
                return;
            }

            List<Map<String, Object>> dataset = mapper.readValue(
                    input,
                    new TypeReference<>() {}
            );

            System.out.println("☕ Loading coffee futures dataset...");
            System.out.println("─────────────────────────────────────────────");

            for (var record : dataset) {
                String month = (String) record.get("month");
                var weather = (Map<String, Object>) record.get("weather");
                var whale = (Map<String, Object>) record.get("whaleActivity");
                var exchange = (Map<String, Object>) record.get("exchangeRate");
                var stock = (Map<String, Object>) record.get("stockDemand");
                var sentiments = (List<Map<String, Object>>) record.get("sentiment");

                // Inserções no Drools
                ksession.insert(new Weather(
                        (String) weather.get("region"),
                        (String) weather.get("condition"),
                        ((Number) weather.get("tempAnomaly")).doubleValue(),
                        ((Number) weather.get("rainAnomaly")).doubleValue(),
                        ((Number) weather.get("impact")).intValue()
                ));

                for (var s : sentiments) {
                    ksession.insert(new Sentiment(
                            (String) s.get("source"),
                            (String) s.get("tone"),
                            ((Number) s.get("score")).doubleValue(),
                            7
                    ));
                }

                ksession.insert(new WhaleActivity(
                        (String) whale.get("period"),
                        ((Number) whale.get("volume")).intValue(),
                        ((Number) whale.get("avgTradeSize")).intValue(),
                        ((Number) whale.get("oiChange")).doubleValue(),
                        ((Number) whale.get("avgVolume7d")).intValue(),
                        ((Number) whale.get("avgOIChange7d")).doubleValue()
                ));

                ksession.insert(new ExchangeRate(
                        (String) exchange.get("pair"),
                        ((Number) exchange.get("value")).doubleValue(),
                        ((Number) exchange.get("volatility")).doubleValue(),
                        ((Number) exchange.get("changePct")).doubleValue(),
                        7
                ));

                ksession.insert(new StockDemand(
                        (String) stock.get("region"),
                        ((Number) stock.get("stockLevel")).intValue(),
                        ((Number) stock.get("avgStockLevel")).intValue(),
                        ((Number) stock.get("demandChange")).doubleValue(),
                        ((Number) stock.get("exportChange")).doubleValue(),
                        7
                ));

                // 🔍 Log bonito e legível
                System.out.printf(
                        "📅 %s\n" +
                                "  🌦️  Weather: %-10s (%s) | ΔTemp: %.1f°C | ΔRain: %.1fmm | Impact: %d\n" +
                                "  🗞️  Sentiment: %s %.2f | %s %.2f\n" +
                                "  🐋  Whale: Volume %d | AvgTrade %d | OI %.1f%% | 7dVol %d\n" +
                                "  💱  FX %s: %.2f (vol %.1f | Δ%.1f%%)\n" +
                                "  📦  Stock: %d / %d | Demand Δ%.1f%% | Export Δ%.1f%%\n" +
                                "─────────────────────────────────────────────\n",
                        month,
                        weather.get("condition"), weather.get("region"),
                        ((Number) weather.get("tempAnomaly")).doubleValue(),
                        ((Number) weather.get("rainAnomaly")).doubleValue(),
                        ((Number) weather.get("impact")).intValue(),
                        sentiments.get(0).get("source"), ((Number) sentiments.get(0).get("score")).doubleValue(),
                        sentiments.get(1).get("source"), ((Number) sentiments.get(1).get("score")).doubleValue(),
                        ((Number) whale.get("volume")).intValue(),
                        ((Number) whale.get("avgTradeSize")).intValue(),
                        ((Number) whale.get("oiChange")).doubleValue(),
                        ((Number) whale.get("avgVolume7d")).intValue(),
                        exchange.get("pair"),
                        ((Number) exchange.get("value")).doubleValue(),
                        ((Number) exchange.get("volatility")).doubleValue(),
                        ((Number) exchange.get("changePct")).doubleValue(),
                        ((Number) stock.get("stockLevel")).intValue(),
                        ((Number) stock.get("avgStockLevel")).intValue(),
                        ((Number) stock.get("demandChange")).doubleValue(),
                        ((Number) stock.get("exportChange")).doubleValue()
                );
            }

            System.out.println("✅ Dataset fully loaded into Drools session!");

        } catch (Exception e) {
            System.err.println("❌ Error loading dataset: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
