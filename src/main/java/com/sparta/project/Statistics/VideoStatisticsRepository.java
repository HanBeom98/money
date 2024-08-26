package com.sparta.project.Statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VideoStatisticsRepository extends JpaRepository<VideoStatistics, Long> {

    @Query("SELECT vs FROM VideoStatistics vs WHERE vs.periodType = :periodType AND vs.startDate <= :endDate AND vs.endDate >= :startDate ORDER BY vs.views DESC")
    List<VideoStatistics> findTop5ByPeriodTypeAndDateRangeOrderByViewsDesc(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("periodType") VideoStatistics.PeriodType periodType);

    @Query("SELECT vs FROM VideoStatistics vs WHERE vs.periodType = :periodType AND vs.startDate <= :endDate AND vs.endDate >= :startDate ORDER BY vs.watchTime DESC")
    List<VideoStatistics> findTop5ByPeriodTypeAndDateRangeOrderByWatchTimeDesc(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("periodType") VideoStatistics.PeriodType periodType);
}
