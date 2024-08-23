package com.sparta.project.Video;

import com.sparta.project.Advertisement.Advertisement;
import com.sparta.project.ViewHistory.ViewHistory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String url;

    // views 필드를 기본값 0으로 설정하여 null이 되지 않도록 합니다.
    private Long views = 0L;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<ViewHistory> viewHistories;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Advertisement> advertisements;

    public Video(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.views = 0L; // 생성자에서도 기본값 설정
    }
}
