package com.sparta.videoservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adContent;
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;
}
