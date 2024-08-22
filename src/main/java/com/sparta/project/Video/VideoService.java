package com.sparta.project.Video;

import com.sparta.project.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

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

    public Video saveVideoWithUrl(String title, String description) {
        // S3에 이미 존재하는 파일의 URL을 가져옴
        String bucketName = "chohanbeom-bucket"; // 실제 S3 버킷 이름
        String keyName = "SACap 2024-06-27 17-20-21-042.mp4"; // S3에 저장된 파일의 키

        String url = s3Service.getExistingFileUrl(bucketName, keyName);

        // URL을 사용해 Video 엔터티를 생성하고 데이터베이스에 저장
        Video video = new Video(title, description, url);
        return videoRepository.save(video);
    }

    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }
}
