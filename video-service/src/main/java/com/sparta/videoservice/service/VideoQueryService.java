package com.sparta.videoservice.service;

import com.sparta.videoservice.model.Video;
import com.sparta.videoservice.repository.VideoReadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 성능 최적화를 위해 읽기 전용 트랜잭션 사용
public class VideoQueryService {

    private final VideoReadRepository videoReadRepository;

    public VideoQueryService(VideoReadRepository videoReadRepository) {
        this.videoReadRepository = videoReadRepository;
    }

    public List<Video> getAllVideos() {
        return videoReadRepository.findAll();
    }

    public Video getVideoById(Long videoId) {
        return videoReadRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }
}
