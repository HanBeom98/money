package com.sparta.project.Advertisement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sparta.project.Video.Video;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adContent;

    // viewCount를 null이 되지 않도록 기본값을 설정합니다.
    private Long viewCount = 0L;

    @ManyToOne
    @JoinColumn(name = "video_id")  // 'videos' 테이블 참조
    private Video video;

    public Advertisement(String adContent, Video video) {
        this.adContent = adContent;
        this.video = video;
        this.viewCount = 0L; // 생성자에서도 기본값 설정
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
