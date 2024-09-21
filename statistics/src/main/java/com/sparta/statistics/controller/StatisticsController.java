package com.sparta.statistics.controller;

import com.sparta.statistics.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/api/statistics/top-videos")
    public List<Long> getTopVideos(@RequestParam String periodType, @RequestParam String metric) {
        return statisticsService.getTopVideos(periodType, metric);
    }
}
