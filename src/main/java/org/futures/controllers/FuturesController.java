package org.futures.controllers;

import org.futures.model.EvaluationResult;
import org.futures.model.FinalResponse;
import org.futures.model.MarketSignal;
import org.futures.services.FuturesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class FuturesController {

    private final FuturesService futuresService;

    public FuturesController(FuturesService futuresService) {
        this.futuresService = futuresService;
    }

    @GetMapping("/evaluate")
    public FinalResponse evaluate(
            @RequestParam String commodity,
            @RequestParam(defaultValue = "3") int horizon,
            @RequestParam(defaultValue = "Conservative") String investor
    ) {
        return futuresService.evaluateCommodity(commodity, horizon, investor);
    }
}