package com.sparta.statistics.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class VideoStatistics {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String periodType; // DAILY, WEEKLY, MONTHLY
    private Date startDate;
    private Date endDate;
    private Long views;
    private Long watchTime;

    @Column(name = "video_id")
    private Long videoId;
}
