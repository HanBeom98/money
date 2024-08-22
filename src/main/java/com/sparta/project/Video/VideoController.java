package com.sparta.project.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.findAllVideos();
    }

    @PostMapping
    public Video createVideo(@RequestBody Video video) {
        return videoService.saveVideo(video);
    }

    @PostMapping("/upload")
    public Video uploadVideo(@RequestBody VideoRequest videoRequest) {
        return videoService.saveVideoWithUrl(videoRequest.getTitle(), videoRequest.getDescription());
    }

    @GetMapping("/{id}")
    public Video getVideoById(@PathVariable Long id) {
        return videoService.findVideoById(id);
    }
}
