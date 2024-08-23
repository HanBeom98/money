package com.sparta.project.Video;

import com.sparta.project.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private S3Service s3Service;

    // 모든 비디오를 조회하여 DTO 리스트로 반환
    public List<VideoDto> findAllVideos() {
        return videoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 비디오를 저장하는 메서드, DTO를 엔티티로 변환 후 저장
    public VideoDto saveVideo(VideoDto videoDto) {
        Video video = convertToEntity(videoDto);
        Video savedVideo = videoRepository.save(video);
        return convertToDto(savedVideo);
    }

    // 비디오를 S3 URL과 함께 저장하는 메서드
    @Transactional
    public VideoDto saveVideoWithUrl(String title, String description) {
        String bucketName = "chohanbeom-bucket";
        String keyName = "SACap 2024-06-27 17-20-21-042.mp4";
        String url = s3Service.getExistingFileUrl(bucketName, keyName);

        Video video = new Video(title, description, url);
        Video savedVideo = videoRepository.save(video);

        return convertToDto(savedVideo);
    }

    // ID로 비디오를 조회하여 DTO로 반환
    public VideoDto findVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        return convertToDto(video);
    }

    // 비디오 조회수를 증가시키는 메서드
    public void incrementViewCount(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
    }

    // 엔티티를 DTO로 변환
    private VideoDto convertToDto(Video video) {
        VideoDto dto = new VideoDto();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setUrl(video.getUrl());
        dto.setViews(video.getViews() != null ? video.getViews() : 0L); // Null 체크 및 기본값 설정
        return dto;
    }

    // DTO를 엔티티로 변환
    private Video convertToEntity(VideoDto dto) {
        Video video = new Video();
        video.setId(dto.getId());
        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setUrl(dto.getUrl());
        video.setViews(dto.getViews() != null ? dto.getViews() : 0L); // Null 체크 및 기본값 설정
        return video;
    }
}
