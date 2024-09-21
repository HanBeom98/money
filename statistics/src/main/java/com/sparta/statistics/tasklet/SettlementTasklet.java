package com.sparta.statistics.tasklet;

import com.sparta.videoservice.service.SettlementService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class SettlementTasklet implements Tasklet {

    private final SettlementService settlementService;

    public SettlementTasklet(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        settlementService.processSettlement();  // 정산 로직 호출
        return RepeatStatus.FINISHED;
    }
}
