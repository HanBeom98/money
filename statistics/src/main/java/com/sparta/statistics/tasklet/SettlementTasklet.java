package com.sparta.statistics.tasklet;

import com.sparta.videoservice.service.SettlementService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class SettlementTasklet implements Tasklet {

    private final SettlementService settlementService;
    private final RetryTemplate retryTemplate;  // RetryTemplate 추가

    public SettlementTasklet(SettlementService settlementService, RetryTemplate retryTemplate) {
        this.settlementService = settlementService;
        this.retryTemplate = retryTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        retryTemplate.execute(context -> {
            settlementService.processSettlement();  // 정산 로직 호출
            return null;
        });
        return RepeatStatus.FINISHED;
    }
}
