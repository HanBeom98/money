package com.sparta.project.Statistics;

import com.sparta.project.Video.Video;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VideoStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;  // 통계 기간의 시작 날짜
    private LocalDate endDate;    // 통계 기간의 종료 날짜

    @Enumerated(EnumType.STRING)
    private PeriodType periodType;  // 일간, 주간, 월간

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    private Long views;

    private Long watchTime;

    public enum PeriodType {
        DAILY,
        WEEKLY,
        MONTHLY
    }
}
