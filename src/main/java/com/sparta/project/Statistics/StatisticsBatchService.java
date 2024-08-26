package com.sparta.project.Statistics;

import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class StatisticsBatchService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsBatchService.class);

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoStatisticsRepository statisticsRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void generateDailyStatistics() {
        try {
            LocalDate today = LocalDate.now();
            generateStatistics(today, today, VideoStatistics.PeriodType.DAILY);
        } catch (Exception e) {
            logger.error("Error generating daily statistics", e);
        }
    }

    @Scheduled(cron = "0 0 0 * * MON") // 매주 월요일 자정에 실행
    public void generateWeeklyStatistics() {
        try {
            LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            LocalDate endOfWeek = startOfWeek.plusDays(6);
            generateStatistics(startOfWeek, endOfWeek, VideoStatistics.PeriodType.WEEKLY);
        } catch (Exception e) {
            logger.error("Error generating weekly statistics", e);
        }
    }

    @Scheduled(cron = "0 0 0 1 * ?") // 매월 1일 자정에 실행
    public void generateMonthlyStatistics() {
        try {
            LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());
            generateStatistics(firstDayOfMonth, lastDayOfMonth, VideoStatistics.PeriodType.MONTHLY);
        } catch (Exception e) {
            logger.error("Error generating monthly statistics", e);
        }
    }

    private void generateStatistics(LocalDate startDate, LocalDate endDate, VideoStatistics.PeriodType periodType) {
        List<Video> videos = videoRepository.findAll();

        for (Video video : videos) {
            try {
                VideoStatistics stats = new VideoStatistics();
                stats.setStartDate(startDate);
                stats.setEndDate(endDate);
                stats.setPeriodType(periodType);
                stats.setVideo(video);
                stats.setViews(video.getViews());
                stats.setWatchTime(calculateWatchTime(video));
                statisticsRepository.save(stats);
            } catch (Exception e) {
                logger.error("Error saving statistics for video " + video.getId(), e);
            }
        }
    }

    private Long calculateWatchTime(Video video) {
        // 전체 시청 시간을 계산하는 로직
        return video.getViewHistories().stream()
                .mapToLong(history -> history.getWatchTime())
                .sum();
    }
}
