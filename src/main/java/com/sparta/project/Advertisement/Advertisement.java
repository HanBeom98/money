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
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "video_id")  // 'videos' 테이블 참조
    private Video video;

    public Advertisement(String adContent, Video video) {
        this.adContent = adContent;
        this.video = video;
        this.viewCount = 0L;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
