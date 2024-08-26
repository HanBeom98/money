package com.sparta.project.Advertisement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sparta.project.Video.Video;

/**
 * Advertisement 엔티티는 비디오에 연결된 광고를 나타냅니다.
 * 광고 콘텐츠, 조회 수, 연결된 비디오와의 관계를 관리합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adContent;

    // 광고 조회 수를 나타내며, 기본값은 0으로 설정합니다.
    private Long viewCount = 0L;

    // 광고가 연결된 비디오를 나타내는 다대일 관계입니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    // 광고 생성자
    public Advertisement(String adContent, Video video) {
        this.adContent = adContent;
        this.video = video;
        this.viewCount = 0L;
    }

    // 광고 조회수를 1 증가시키는 메서드
    public void incrementViewCount() {
        this.viewCount++;
    }
}
