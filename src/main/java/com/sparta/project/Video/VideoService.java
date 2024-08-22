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

    public Video saveVideoWithUpload(String title, String description, String filePath) {
        String bucketName = "chohanbeom-bucket"; // 실제 S3 버킷 이름으로 변경하세요
        String keyName = "videos/" + title.replaceAll(" ", "_"); // 파일의 고유한 키 설정

        String url = s3Service.uploadFileAndGetUrl(bucketName, keyName, filePath);

        Video video = new Video(title, description, url);
        return videoRepository.save(video);
    }

    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }
}
