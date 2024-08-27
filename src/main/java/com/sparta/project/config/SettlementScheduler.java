package com.sparta.project.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SettlementScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job settlementJob;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void runSettlementJob() throws Exception {
        jobLauncher.run(settlementJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }
}
