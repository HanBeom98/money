package com.sparta.statistics.service;

import com.sparta.statistics.repository.VideoStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final VideoStatisticsRepository videoStatisticsRepository;

    public StatisticsService(VideoStatisticsRepository videoStatisticsRepository) {
        this.videoStatisticsRepository = videoStatisticsRepository;
    }

    public List<Long> getTopVideos(String periodType, String metric) {
        if ("views".equals(metric)) {
            return videoStatisticsRepository.findTop5ByViews(periodType);
        } else if ("watch_time".equals(metric)) {
            return videoStatisticsRepository.findTop5ByWatchTime(periodType);
        }
        return null;
    }
}
