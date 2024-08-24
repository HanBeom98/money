package com.sparta.project.Video;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    // 모든 비디오를 조회하여 반환
    @GetMapping
    public ResponseEntity<List<VideoDto>> getAllVideos() {
        List<VideoDto> videos = videoService.findAllVideos();
        return ResponseEntity.ok(videos);
    }

    // 비디오를 생성하는 엔드포인트
    @PostMapping
    public ResponseEntity<VideoDto> createVideo(@RequestBody VideoDto videoDto) {
        VideoDto createdVideo = videoService.saveVideo(videoDto);
        return ResponseEntity.ok(createdVideo);
    }

    // 비디오를 ID로 조회하여 반환
    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Long id) {
        VideoDto videoDto = videoService.findVideoById(id);
        return ResponseEntity.ok(videoDto);
    }

    // 비디오 조회수 증가 엔드포인트
    @PatchMapping("/{id}/view")
    public ResponseEntity<Void> incrementVideoViewCount(@PathVariable Long id) {
        videoService.incrementViewCount(id);
        return ResponseEntity.noContent().build();
    }

    // 비디오 업데이트 엔드포인트 추가
    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable Long id, @Valid @RequestBody VideoDto videoDto) {
        videoDto.setId(id);
        VideoDto updatedVideo = videoService.saveVideo(videoDto);
        return ResponseEntity.ok(updatedVideo);
    }

    // 비디오 삭제 엔드포인트 추가
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}
