package org.futures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.futures.model.*;
import org.kie.api.runtime.KieSession;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class CoffeeDatasetLoader {

//    public static void loadDataset(KieSession ksession) {
//        ObjectMapper mapper = new ObjectMapper();
//
//        try (InputStream input = CoffeeDatasetLoader.class
//                .getClassLoader()
//                .getResourceAsStream("dataset/coffee_futures_dataset.json")) {
//
//            if (input == null) {
//                System.err.println("❌ Dataset not found in resources folder!");
//                return;
//            }
//
//            List<Map<String, Object>> dataset = mapper.readValue(
//                    input,
//                    new TypeReference<>() {}
//            );
//
//            System.out.println("☕ Loading coffee futures dataset...");
//            System.out.println("─────────────────────────────────────────────");
//
//            for (var record : dataset) {
//                String month = (String) record.get("month");
//                var weather = (Map<String, Object>) record.get("weather");
//                var whale = (Map<String, Object>) record.get("whaleActivity");
//                var exchange = (Map<String, Object>) record.get("exchangeRate");
//                var stock = (Map<String, Object>) record.get("stockDemand");
//                var sentiments = (List<Map<String, Object>>) record.get("sentiment");
//
//                // Inserções no Drools
//                ksession.insert(new Weather(
//                        (String) weather.get("region"),
//                        (String) weather.get("condition"),
//                        ((Number) weather.get("tempAnomaly")).doubleValue(),
//                        ((Number) weather.get("rainAnomaly")).doubleValue(),
//                        ((Number) weather.get("impact")).intValue()
//                ));
//
//                for (var s : sentiments) {
//                    ksession.insert(new Sentiment(
//                            (String) s.get("source"),
//                            (String) s.get("tone"),
//                            ((Number) s.get("score")).doubleValue(),
//                            7
//                    ));
//                }
//
//                ksession.insert(new WhaleActivity(
//                        (String) whale.get("period"),
//                        ((Number) whale.get("volume")).intValue(),
//                        ((Number) whale.get("avgTradeSize")).intValue(),
//                        ((Number) whale.get("oiChange")).doubleValue(),
//                        ((Number) whale.get("avgVolume7d")).intValue(),
//                        ((Number) whale.get("avgOIChange7d")).doubleValue()
//                ));
//
//                ksession.insert(new ExchangeRate(
//                        (String) exchange.get("pair"),
//                        ((Number) exchange.get("value")).doubleValue(),
//                        ((Number) exchange.get("volatility")).doubleValue(),
//                        ((Number) exchange.get("changePct")).doubleValue(),
//                        7
//                ));
//
//                ksession.insert(new StockDemand(
//                        (String) stock.get("region"),
//                        ((Number) stock.get("stockLevel")).intValue(),
//                        ((Number) stock.get("avgStockLevel")).intValue(),
//                        ((Number) stock.get("demandChange")).doubleValue(),
//                        ((Number) stock.get("exportChange")).doubleValue(),
//                        7
//                ));
//
//                // 🔍 Log bonito e legível
//                System.out.printf(
//                        "📅 %s\n" +
//                                "  🌦️  Weather: %-10s (%s) | ΔTemp: %.1f°C | ΔRain: %.1fmm | Impact: %d\n" +
//                                "  🗞️  Sentiment: %s %.2f | %s %.2f\n" +
//                                "  🐋  Whale: Volume %d | AvgTrade %d | OI %.1f%% | 7dVol %d\n" +
//                                "  💱  FX %s: %.2f (vol %.1f | Δ%.1f%%)\n" +
//                                "  📦  Stock: %d / %d | Demand Δ%.1f%% | Export Δ%.1f%%\n" +
//                                "─────────────────────────────────────────────\n",
//                        month,
//                        weather.get("condition"), weather.get("region"),
//                        ((Number) weather.get("tempAnomaly")).doubleValue(),
//                        ((Number) weather.get("rainAnomaly")).doubleValue(),
//                        ((Number) weather.get("impact")).intValue(),
//                        sentiments.get(0).get("source"), ((Number) sentiments.get(0).get("score")).doubleValue(),
//                        sentiments.get(1).get("source"), ((Number) sentiments.get(1).get("score")).doubleValue(),
//                        ((Number) whale.get("volume")).intValue(),
//                        ((Number) whale.get("avgTradeSize")).intValue(),
//                        ((Number) whale.get("oiChange")).doubleValue(),
//                        ((Number) whale.get("avgVolume7d")).intValue(),
//                        exchange.get("pair"),
//                        ((Number) exchange.get("value")).doubleValue(),
//                        ((Number) exchange.get("volatility")).doubleValue(),
//                        ((Number) exchange.get("changePct")).doubleValue(),
//                        ((Number) stock.get("stockLevel")).intValue(),
//                        ((Number) stock.get("avgStockLevel")).intValue(),
//                        ((Number) stock.get("demandChange")).doubleValue(),
//                        ((Number) stock.get("exportChange")).doubleValue()
//                );
//            }
//
//            System.out.println("✅ Dataset fully loaded into Drools session!");
//
//        } catch (Exception e) {
//            System.err.println("❌ Error loading dataset: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public static void loadDataset(KieSession ksession) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream input = CoffeeDatasetLoader.class
                .getClassLoader()
                .getResourceAsStream("dataset/dataset.json")) {

            if (input == null) {
                System.err.println("❌ Dataset not found in resources folder!");
                return;
            }

            Map<String, Object> root = mapper.readValue(input, new TypeReference<>() {});
            System.out.println("☕ Loading NEW coffee dataset...");
            System.out.println("─────────────────────────────────────────────");

            // =============================
            // 💱 Exchange Rates
            // =============================
            List<Map<String, Object>> exchangeRates = (List<Map<String, Object>>) root.get("exchange_rate");
            for (var record : exchangeRates) {
                ExchangeRate fx = new ExchangeRate(
                        (String) record.get("country"),
                        ((Number) record.get("month")).intValue(),
                        ((Number) record.get("variation")).doubleValue()
                );
                ksession.insert(fx);

                System.out.printf(
                        "💱 %-10s | Month: %02d | ΔRate: %.2f%%%n",
                        fx.getCountry(),
                        fx.getMonth(),
                        fx.getVariation()
                );
            }

            // =============================
            // 🏭 Production Data
            // =============================
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

                System.out.printf(
                        "🏭 %-10s | %-8s | %-15s | M%02d | Production: %.2f%n",
                        prod.getCountry(),
                        prod.getCommodity(),
                        prod.getRegion(),
                        prod.getMonth(),
                        prod.getProduction()
                );
            }

            // =============================
            // 🌦️ Weather Data
            // =============================
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

                System.out.printf(
                        "🌦️  %-10s | %-15s | %-10s | Temp: %.1f°C | SPI: %.2f%n",
                        weather.getCountry(),
                        weather.getRegion(),
                        weather.getMonth(),
                        weather.getTemperature(),
                        weather.getSpi()
                );
            }

            // =============================
            // 📊 Volume & Market Activity
            // =============================
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

                System.out.printf(
                        "📊 %-8s | M%02d | Vol: %.1f | 7d: %.1f | OI: %.1f | Trade: %d%n",
                        vol.getCommodity(),
                        vol.getMonth(),
                        vol.getBuyVolume(),
                        vol.getBuyVolume7d(),
                        vol.getOpenInterest(),
                        vol.getTradeSize()
                );
            }

            System.out.println("─────────────────────────────────────────────");
            System.out.println("✅ New dataset fully loaded into Drools session!");

        } catch (Exception e) {
            System.err.println("❌ Error loading dataset: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
