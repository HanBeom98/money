package com.sparta.project.Video;

import com.sparta.project.User.User;
import com.sparta.project.s3.S3Service;
import com.sparta.project.Util.EntityDtoConverter;

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

    public List<VideoDto> findAllVideos() {
        return videoRepository.findAll().stream()
                .map(EntityDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public VideoDto saveVideo(VideoDto videoDto, User uploader) {
        Video video = EntityDtoConverter.convertToEntity(videoDto, uploader);
        Video savedVideo = videoRepository.save(video);
        return EntityDtoConverter.convertToDto(savedVideo);
    }

    @Transactional
    public VideoDto saveVideoWithUrl(String title, String description, User uploader) {
        String bucketName = "chohanbeom-bucket";
        String keyName = "SACap 2024-06-27 17-20-21-042.mp4";
        String url = s3Service.getExistingFileUrl(bucketName, keyName);

        Video video = new Video(title, description, url, uploader);
        Video savedVideo = videoRepository.save(video);

        return EntityDtoConverter.convertToDto(savedVideo);
    }

    public VideoDto findVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        return EntityDtoConverter.convertToDto(video);
    }

    public void incrementViewCount(Long videoId, User currentUser) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        if (!currentUser.equals(video.getUploader())) {
            video.setViews(video.getViews() + 1);
            videoRepository.save(video);
        }
    }

    public void deleteVideo(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        videoRepository.delete(video);
    }
}
