package com.sparta.project;

import com.sparta.project.Settlement.SettlementBatchConfig;
import com.sparta.project.Video.Video;
import com.sparta.project.Advertisement.Advertisement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SettlementBatchConfigTest {

    @Test
    @DisplayName("비디오 정산계산")
    public void testCalculateVideoSettlement() {
        SettlementBatchConfig settlementBatchConfig = new SettlementBatchConfig(null, null);

        // 가상의 조회수를 기반으로 정산 금액 계산
        Long views = 200000L;  // 비디오 조회수
        Long expectedVideoSettlement = settlementBatchConfig.calculateVideoSettlement(views);  // 기대하는 비디오 정산 금액

        // 실제 정산 금액과 비교 (테스트용 기대값을 설정)
        Long actualVideoSettlement = 220000L;  // 실제 기대하는 값
        System.out.println("비디오 정산 금액: " + expectedVideoSettlement);

        // 실제 계산된 값과 기대값 비교
        assertEquals(expectedVideoSettlement, actualVideoSettlement);
    }

    @Test
    public void testCalculateAdSettlement() {
        SettlementBatchConfig settlementBatchConfig = new SettlementBatchConfig(null, null);

        // 가상의 비디오 객체와 광고 객체 생성
        Video video = new Video("Test Title", "Test Description", "http://test-url.com", null);
        Advertisement ad = new Advertisement();
        ad.setViewCount(50000L); // 광고 조회수 설정
        video.getAdvertisements().add(ad); // 광고 객체를 광고 리스트에 추가

        // 가상의 광고 조회수를 기반으로 정산 금액 계산
        Long expectedAdSettlement = settlementBatchConfig.calculateAdSettlement(video); // 기대하는 광고 정산 금액

        // 실제 정산 금액과 비교 (테스트용 기대값을 설정)
        Long actualAdSettlement = 500000L;  // 실제 기대하는 값
        System.out.println("광고 정산 금액: " + expectedAdSettlement);

        // 실제 계산된 값과 기대값 비교
        assertEquals(expectedAdSettlement, actualAdSettlement);
    }
}
