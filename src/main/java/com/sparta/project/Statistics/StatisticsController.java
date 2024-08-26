package com.sparta.project.Statistics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * StatisticsController는 비디오 통계와 관련된 API 요청을 처리합니다.
 * 조회 수 상위 비디오, 시청 시간 상위 비디오 등을 조회하는 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final VideoStatisticsRepository statisticsRepository;

    public StatisticsController(VideoStatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    // 조회 수 상위 비디오를 조회하는 엔드포인트
    @GetMapping("/top-views")
    public ResponseEntity<List<VideoStatistics>> getTopViewedVideos(
            @RequestParam LocalDate date,
            @RequestParam VideoStatistics.PeriodType periodType
    ) {
        LocalDate startDate = calculateStartDate(date, periodType);
        LocalDate endDate = calculateEndDate(date, periodType);

        List<VideoStatistics> topViewedVideos = statisticsRepository.findTop5ByPeriodTypeAndDateRangeOrderByViewsDesc(startDate, endDate, periodType);
        return ResponseEntity.ok(topViewedVideos);
    }

    // 시청 시간 상위 비디오를 조회하는 엔드포인트
    @GetMapping("/top-watch-time")
    public ResponseEntity<List<VideoStatistics>> getTopWatchTimeVideos(
            @RequestParam LocalDate date,
            @RequestParam VideoStatistics.PeriodType periodType
    ) {
        LocalDate startDate = calculateStartDate(date, periodType);
        LocalDate endDate = calculateEndDate(date, periodType);

        List<VideoStatistics> topWatchTimeVideos = statisticsRepository.findTop5ByPeriodTypeAndDateRangeOrderByWatchTimeDesc(startDate, endDate, periodType);
        return ResponseEntity.ok(topWatchTimeVideos);
    }

    // 주어진 날짜와 기간 유형에 따라 시작 날짜를 계산하는 메서드
    private LocalDate calculateStartDate(LocalDate date, VideoStatistics.PeriodType periodType) {
        switch (periodType) {
            case DAILY:
                return date;
            case WEEKLY:
                return date.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            case MONTHLY:
                return date.with(java.time.temporal.TemporalAdjusters.firstDayOfMonth());
            default:
                throw new IllegalArgumentException("Invalid period type");
        }
    }

    // 주어진 날짜와 기간 유형에 따라 종료 날짜를 계산하는 메서드
    private LocalDate calculateEndDate(LocalDate date, VideoStatistics.PeriodType periodType) {
        switch (periodType) {
            case DAILY:
                return date;
            case WEEKLY:
                return date.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
            case MONTHLY:
                return date.with(java.time.temporal.TemporalAdjusters.lastDayOfMonth());
            default:
                throw new IllegalArgumentException("Invalid period type");
        }
    }
}
