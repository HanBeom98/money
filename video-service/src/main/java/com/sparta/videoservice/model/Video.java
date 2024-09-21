package com.sparta.videoservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String title;
    private String url;
    private Long views;

    @Getter
    @Column(name = "uploader_id")
    private Long uploaderId;

    private Date viewDate;
    private Long watchTime;

}
