package com.sparta.statistics.config;

import com.sparta.statistics.tasklet.StatisticsTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
@EnableBatchProcessing
public class StatisticsBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public StatisticsBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job statisticsJob(
            @Qualifier("dailyStatisticsStep") Step dailyStatisticsStep,
            @Qualifier("weeklyStatisticsStep") Step weeklyStatisticsStep,
            @Qualifier("monthlyStatisticsStep") Step monthlyStatisticsStep) {
        return new JobBuilder("statisticsJob", jobRepository)
                .start(dailyStatisticsStep)
                .next(weeklyStatisticsStep)
                .next(monthlyStatisticsStep)
                .build();
    }

    @Bean
    @Qualifier("dailyStatisticsStep")
    public Step dailyStatisticsStep(StatisticsTasklet dailyStatisticsTasklet) {
        return new StepBuilder("dailyStatisticsStep", jobRepository)
                .tasklet(dailyStatisticsTasklet, transactionManager)
                .build();
    }

    @Bean
    @Qualifier("weeklyStatisticsStep")
    public Step weeklyStatisticsStep(StatisticsTasklet weeklyStatisticsTasklet) {
        return new StepBuilder("weeklyStatisticsStep", jobRepository)
                .tasklet(weeklyStatisticsTasklet, transactionManager)
                .build();
    }

    @Bean
    @Qualifier("monthlyStatisticsStep")
    public Step monthlyStatisticsStep(StatisticsTasklet monthlyStatisticsTasklet) {
        return new StepBuilder("monthlyStatisticsStep", jobRepository)
                .tasklet(monthlyStatisticsTasklet, transactionManager)
                .build();
    }
}
