package com.sparta.videoservice.service;

import com.sparta.videoservice.model.Video;
import com.sparta.videoservice.repository.VideoReadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class SettlementService {

    private final VideoReadRepository videoReadRepository;

    public SettlementService(VideoReadRepository videoReadRepository) {
        this.videoReadRepository = videoReadRepository;
    }

    // 동영상 조회수에 따른 정산 금액 계산
    public BigDecimal calculateVideoRevenue(Long views) {
        if (views < 100_000) {
            return BigDecimal.valueOf(views).multiply(BigDecimal.ONE); // 1원/조회
        } else if (views < 500_000) {
            return BigDecimal.valueOf(views).multiply(BigDecimal.valueOf(1.1)).subtract(BigDecimal.valueOf(10_000));
        } else if (views < 1_000_000) {
            return BigDecimal.valueOf(views).multiply(BigDecimal.valueOf(1.3)).subtract(BigDecimal.valueOf(90_000));
        } else {
            return BigDecimal.valueOf(views).multiply(BigDecimal.valueOf(1.5)).subtract(BigDecimal.valueOf(240_000));
        }
    }

    // 광고 조회수에 따른 정산 금액 계산
    public BigDecimal calculateAdRevenue(Long adViews) {
        if (adViews < 100_000) {
            return BigDecimal.valueOf(adViews).multiply(BigDecimal.TEN); // 10원/조회
        } else if (adViews < 500_000) {
            return BigDecimal.valueOf(adViews).multiply(BigDecimal.valueOf(12)).subtract(BigDecimal.valueOf(200_000));
        } else if (adViews < 1_000_000) {
            return BigDecimal.valueOf(adViews).multiply(BigDecimal.valueOf(15)).subtract(BigDecimal.valueOf(800_000));
        } else {
            return BigDecimal.valueOf(adViews).multiply(BigDecimal.valueOf(20)).subtract(BigDecimal.valueOf(2_300_000));
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal getSettlement(Long videoId, String periodType) {
        // 실제 데이터 조회
        Long videoViews = videoReadRepository.findById(videoId)
                .map(Video::getViews)
                .orElse(0L);

        // 광고 조회수는 별도 로직으로 조회
        Long adViews = getAdViewsByVideoId(videoId);

        BigDecimal videoRevenue = calculateVideoRevenue(videoViews);
        BigDecimal adRevenue = calculateAdRevenue(adViews);

        return videoRevenue.add(adRevenue); // 전체 정산 금액 반환
    }

    // 광고 조회수 조회 로직 (추가된 부분)
    private Long getAdViewsByVideoId(Long videoId) {
        // 광고 조회수 조회를 위한 실제 로직 구현 필요 (예시로 550,000 조회수를 반환)
        return 550_000L; // 실제 로직으로 대체 필요
    }

    public void processSettlement() {
        // 정산 처리 로직 추가 필요
    }
}
