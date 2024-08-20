package com.sparta.project.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }
}
