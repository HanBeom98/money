package com.sparta.project.Video;

import com.sparta.project.s3.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoService.class);

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private S3Service s3Service;

    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    @Transactional
    public Video saveVideoWithUrl(String title, String description) {
        logger.info("Starting saveVideoWithUrl method");

        // S3에 이미 존재하는 파일의 URL을 가져옴
        String bucketName = "chohanbeom-bucket"; // 실제 S3 버킷 이름
        String keyName = "SACap 2024-06-27 17-20-21-042.mp4"; // S3에 저장된 파일의 키

        String url = s3Service.getExistingFileUrl(bucketName, keyName);

        // URL을 사용해 Video 엔터티를 생성하고 데이터베이스에 저장
        Video video = new Video(title, description, url);
        Video savedVideo = videoRepository.save(video);
        logger.info("Video saved with ID: " + savedVideo.getId());

        return savedVideo;
    }

    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }
}
