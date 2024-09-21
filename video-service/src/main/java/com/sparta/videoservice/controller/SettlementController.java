package com.sparta.videoservice.controller;

import com.sparta.videoservice.service.SettlementService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @GetMapping("/video/{videoId}")
    public BigDecimal getVideoSettlement(@PathVariable Long videoId, @RequestParam String periodType) {
        return settlementService.getSettlement(videoId, periodType);
    }
}
