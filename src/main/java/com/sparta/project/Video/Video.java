package com.sparta.project.Video;

import com.sparta.project.Advertisement.Advertisement;
import com.sparta.project.User.User;
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

    private Long views = 0L;

    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private User uploader;  // 동영상 업로더 필드 추가

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<ViewHistory> viewHistories;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Advertisement> advertisements;

    public Video(String title, String description, String url, User uploader) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.uploader = uploader;
        this.views = 0L;
    }
}
