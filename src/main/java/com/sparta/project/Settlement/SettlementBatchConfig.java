package com.sparta.project.Settlement;

import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class SettlementBatchConfig {

    private final VideoRepository videoRepository;
    private final SettlementRepository settlementRepository;

    @Autowired
    public SettlementBatchConfig(VideoRepository videoRepository, SettlementRepository settlementRepository) {
        this.videoRepository = videoRepository;
        this.settlementRepository = settlementRepository;
    }

    @Bean
    public Job settlementJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("settlementJob", jobRepository)
                .start(settlementStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step settlementStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("settlementStep", jobRepository)
                .tasklet(settlementTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet settlementTasklet() {
        return (contribution, chunkContext) -> {
            List<Video> videos = videoRepository.findAll();
            for (Video video : videos) {
                Long videoSettlement = calculateVideoSettlement(video);
                Long adSettlement = calculateAdSettlement(video);
                // 정산 결과 저장 로직 추가
            }
            return RepeatStatus.FINISHED;
        };
    }

    public Long calculateVideoSettlement(Video video) {
        Long views = video.getViews();
        double rate;

        if (views < 500000) {
            rate = 1.1;
        } else if (views < 1000000) {
            rate = 1.3;
        } else {
            rate = 1.5;
        }

        return (long) Math.floor(views * rate);
    }

    public Long calculateVideoSettlement(Long views) {
        double rate;

        if (views < 500000) {
            rate = 1.1;
        } else if (views < 1000000) {
            rate = 1.3;
        } else {
            rate = 1.5;
        }

        return (long) Math.floor(views * rate);
    }

    public long calculateAdSettlement(Video video) {
        Long adViews = video.getAdvertisements().stream()
                .mapToLong(advertisement -> advertisement.getViewCount())
                .sum();
        double rate;

        if (adViews < 100000) {
            rate = 10.0;
        } else if (adViews < 500000) {
            rate = 12.0;
        } else if (adViews < 1000000) {
            rate = 15.0;
        } else {
            rate = 20.0;
        }

        return (long) Math.floor(adViews * rate);
    }
}
