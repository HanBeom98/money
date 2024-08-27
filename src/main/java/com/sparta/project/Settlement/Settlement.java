package com.sparta.project.Settlement;

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
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate settlementDate;

    private Long videoSettlementAmount;

    private Long adSettlementAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    public Settlement(LocalDate settlementDate, Long videoSettlementAmount, Long adSettlementAmount, Video video) {
        this.settlementDate = settlementDate;
        this.videoSettlementAmount = videoSettlementAmount;
        this.adSettlementAmount = adSettlementAmount;
        this.video = video;
    }

    public Long getTotalSettlementAmount() {
        return videoSettlementAmount + adSettlementAmount;
    }
}
