package com.sparta.project.Video;

public class VideoRequest {
    private String title;
    private String description;
    private String filePath;

    // 기본 생성자
    public VideoRequest() {}

    // 파라미터 생성자
    public VideoRequest(String title, String description, String filePath) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
    }

    // Getter와 Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
