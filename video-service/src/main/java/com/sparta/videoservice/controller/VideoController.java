package com.sparta.videoservice.controller;

import com.sparta.videoservice.model.Video;
import com.sparta.videoservice.service.VideoCommandService;
import com.sparta.videoservice.service.VideoQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoCommandService videoCommandService;
    private final VideoQueryService videoQueryService;

    public VideoController(VideoCommandService videoCommandService, VideoQueryService videoQueryService) {
        this.videoCommandService = videoCommandService;
        this.videoQueryService = videoQueryService;
    }

    // 조회 작업: 비디오 목록 가져오기
    @GetMapping
    public List<Video> getAllVideos() {
        return videoQueryService.getAllVideos();
    }

    // 조회 작업: 비디오 단건 조회
    @GetMapping("/{videoId}")
    public Video getVideo(@PathVariable Long videoId) {
        return videoQueryService.getVideoById(videoId);
    }

    // 쓰기 작업: 비디오 생성
    @PostMapping
    public Video createVideo(@RequestBody Video video) {
        return videoCommandService.createVideo(video);
    }

    // 쓰기 작업: 비디오 삭제
    @DeleteMapping("/{videoId}")
    public String deleteVideo(@PathVariable Long videoId) {
        videoCommandService.deleteVideo(videoId);
        return "Video deleted successfully";
    }

    // 쓰기 작업: 비디오 업데이트
    @PutMapping("/{videoId}")
    public Video updateVideo(@PathVariable Long videoId, @RequestBody Video video) {
        video.setId(videoId); // 비디오 ID 설정
        return videoCommandService.updateVideo(video);
    }
}
