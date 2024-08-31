package com.sparta.project.Video;

import com.sparta.project.User.User;
import com.sparta.project.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    // 모든 비디오를 조회하여 반환
    @GetMapping
    public ResponseEntity<List<VideoDto>> getAllVideos() {
        List<VideoDto> videos = videoService.findAllVideos();
        return ResponseEntity.ok(videos);
    }

    // 비디오를 생성하는 엔드포인트
    @PostMapping
    public ResponseEntity<VideoDto> createVideo(@RequestBody VideoDto videoDto) {
        // 현재 로그인한 사용자를 가져옴
        User currentUser = userService.getCurrentUser().block(); // Mono에서 User를 얻어옴
        VideoDto createdVideo = videoService.saveVideo(videoDto, currentUser);
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
        // 현재 로그인한 사용자를 가져옴
        User currentUser = userService.getCurrentUser().block(); // Mono에서 User를 얻어옴
        videoService.incrementViewCount(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
