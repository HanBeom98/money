package com.sparta.project.Video;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDto {
    private Long id;
    private String title;
    private String description;
    private String url;
    private Long views;
}
