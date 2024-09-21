package com.sparta.statistics.repository;


import com.sparta.statistics.model.VideoStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface VideoStatisticsRepository extends JpaRepository<VideoStatistics, Long> {

    @Query("SELECT v.videoId FROM VideoStatistics v WHERE v.periodType = :periodType ORDER BY v.views DESC")
    List<Long> findTop5ByViews(String periodType);

    @Query("SELECT v.videoId FROM VideoStatistics v WHERE v.periodType = :periodType ORDER BY v.watchTime DESC")
    List<Long> findTop5ByWatchTime(String periodType);

    @Query("SELECT SUM(v.views) FROM Video v WHERE v.id = :videoId AND v.viewDate BETWEEN :startDate AND :endDate")
    Long findViewsByVideoId(Long videoId, Date startDate, Date endDate);

    @Query("SELECT SUM(v.watchTime) FROM Video v WHERE v.id = :videoId AND v.viewDate BETWEEN :startDate AND :endDate")
    Long findWatchTimeByVideoId(Long videoId, Date startDate, Date endDate);
}
