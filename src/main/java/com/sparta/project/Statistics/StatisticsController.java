package com.sparta.project.Statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private VideoStatisticsRepository statisticsRepository;

    @GetMapping("/top-views")
    public ResponseEntity<List<VideoStatistics>> getTopViewedVideos(
            @RequestParam LocalDate date,
            @RequestParam VideoStatistics.PeriodType periodType
    ) {
        try {
            LocalDate startDate = calculateStartDate(date, periodType);
            LocalDate endDate = calculateEndDate(date, periodType);

            List<VideoStatistics> topViewedVideos = statisticsRepository.findTop5ByPeriodTypeAndDateRangeOrderByViewsDesc(startDate, endDate, periodType);
            return ResponseEntity.ok(topViewedVideos);
        } catch (Exception e) {
            logger.error("Error retrieving top viewed videos", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/top-watch-time")
    public ResponseEntity<List<VideoStatistics>> getTopWatchTimeVideos(
            @RequestParam LocalDate date,
            @RequestParam VideoStatistics.PeriodType periodType
    ) {
        try {
            LocalDate startDate = calculateStartDate(date, periodType);
            LocalDate endDate = calculateEndDate(date, periodType);

            List<VideoStatistics> topWatchTimeVideos = statisticsRepository.findTop5ByPeriodTypeAndDateRangeOrderByWatchTimeDesc(startDate, endDate, periodType);
            return ResponseEntity.ok(topWatchTimeVideos);
        } catch (Exception e) {
            logger.error("Error retrieving top watch time videos", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    private LocalDate calculateStartDate(LocalDate date, VideoStatistics.PeriodType periodType) {
        switch (periodType) {
            case DAILY:
                return date;
            case WEEKLY:
                return date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            case MONTHLY:
                return date.with(TemporalAdjusters.firstDayOfMonth());
            default:
                throw new IllegalArgumentException("Invalid period type");
        }
    }

    private LocalDate calculateEndDate(LocalDate date, VideoStatistics.PeriodType periodType) {
        switch (periodType) {
            case DAILY:
                return date;
            case WEEKLY:
                return date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
            case MONTHLY:
                return date.with(TemporalAdjusters.lastDayOfMonth());
            default:
                throw new IllegalArgumentException("Invalid period type");
        }
    }
}
