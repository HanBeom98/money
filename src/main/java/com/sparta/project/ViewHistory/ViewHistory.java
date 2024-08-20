package com.sparta.project.ViewHistory;

import com.sparta.project.User.User;
import com.sparta.project.Video.Video;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ViewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime viewDate;
    private Long watchTime;

    @ManyToOne
    @JoinColumn(name = "video_id")  // 'videos' 테이블 참조
    private Video video;

    @ManyToOne
    @JoinColumn(name = "user_id")  // 'users' 테이블 참조
    private User user;

    private Long lastWatchedTime;

    public ViewHistory(Video video, User user, LocalDateTime viewDate, Long watchTime) {
        this.video = video;
        this.user = user;
        this.viewDate = viewDate;
        this.watchTime = watchTime;
        this.lastWatchedTime = 0L;
    }
}
