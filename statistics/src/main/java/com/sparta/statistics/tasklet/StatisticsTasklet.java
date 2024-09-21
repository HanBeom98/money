package com.sparta.statistics.tasklet;

import com.sparta.statistics.model.VideoStatistics;
import com.sparta.statistics.repository.VideoStatisticsRepository;
import lombok.Setter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class StatisticsTasklet implements Tasklet {

    private final VideoStatisticsRepository videoStatisticsRepository;
    @Setter
    private String periodType;

    public StatisticsTasklet(VideoStatisticsRepository videoStatisticsRepository) {
        this.videoStatisticsRepository = videoStatisticsRepository;
    }

    @Override
    @Transactional
    public RepeatStatus execute(@NonNull StepContribution contribution, @NonNull ChunkContext chunkContext) {
        // 조회수와 재생시간이 높은 동영상 5개를 조회하는 로직
        List<Long> topViewedVideos = videoStatisticsRepository.findTop5ByViews(periodType);
        List<Long> topWatchedVideos = videoStatisticsRepository.findTop5ByWatchTime(periodType);

        // 시작일과 종료일 설정
        Date[] period = calculatePeriod(periodType);
        Date startDate = period[0];
        Date endDate = period[1];

        // 처리된 데이터를 통계 테이블에 저장 (topWatchedVideos도 함께 처리)
        topWatchedVideos.forEach(videoId -> {
            VideoStatistics stats = new VideoStatistics();
            stats.setVideoId(videoId);
            stats.setPeriodType(periodType);
            stats.setStartDate(startDate); // 실제 기간에 맞게 설정
            stats.setEndDate(endDate); // 실제 기간에 맞게 설정

            // 실제 데이터를 가져오는 로직을 구현해야 함
            Long videoViews = videoStatisticsRepository.findViewsByVideoId(videoId, startDate, endDate);
            Long videoWatchTime = videoStatisticsRepository.findWatchTimeByVideoId(videoId, startDate, endDate);

            stats.setViews(videoViews);  // 조회된 데이터로 설정
            stats.setWatchTime(videoWatchTime);  // 조회된 데이터로 설정

            videoStatisticsRepository.save(stats);
        });

        return RepeatStatus.FINISHED;
    }

    private Date[] calculatePeriod(String periodType) {
        Calendar calendar = Calendar.getInstance();
        Date startDate;
        Date endDate;

        switch (periodType) {
            case "DAILY":
                // 오늘의 날짜 계산
                startDate = calendar.getTime();
                endDate = calendar.getTime();
                break;
            case "WEEKLY":
                // 해당 주의 월요일과 일요일 계산
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                startDate = calendar.getTime();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                endDate = calendar.getTime();
                break;
            case "MONTHLY":
                // 해당 달의 시작일과 종료일 계산
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startDate = calendar.getTime();
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, -1);
                endDate = calendar.getTime();
                break;
            default:
                throw new IllegalArgumentException("Invalid period type: " + periodType);
        }

        return new Date[]{startDate, endDate};
    }
}
