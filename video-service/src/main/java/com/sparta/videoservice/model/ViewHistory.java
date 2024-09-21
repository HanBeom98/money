package com.sparta.videoservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class ViewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lastWatchedTime;
    private Date viewDate;
    private Long watchTime;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    private String ipAddress;
}
