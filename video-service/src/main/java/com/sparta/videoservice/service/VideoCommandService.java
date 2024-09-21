package com.sparta.videoservice.service;

import com.sparta.videoservice.model.Video;
import com.sparta.videoservice.repository.VideoWriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoCommandService {

    private final VideoWriteRepository videoWriteRepository;

    public VideoCommandService(VideoWriteRepository videoWriteRepository) {
        this.videoWriteRepository = videoWriteRepository;
    }

    @Transactional
    public Video createVideo(Video video) {
        return videoWriteRepository.save(video);
    }

    @Transactional
    public void deleteVideo(Long videoId) {
        Video video = videoWriteRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        videoWriteRepository.delete(video);
    }

    @Transactional
    public Video updateVideo(Video video) {
        return videoWriteRepository.save(video);
    }
}
