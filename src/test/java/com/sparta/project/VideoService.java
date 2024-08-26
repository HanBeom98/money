package com.sparta.project;

import com.sparta.project.User.User;
import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoDto;
import com.sparta.project.Video.VideoRepository;
import com.sparta.project.Video.VideoService;
import com.sparta.project.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private VideoService videoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveVideoWithUrl_shouldSaveVideoAndReturnDto() {
        // Given
        String title = "Test Video";
        String description = "This is a test video";
        String uploaderEmail = "uploader@example.com";
        User uploader = new User();
        uploader.setEmail(uploaderEmail);

        String expectedUrl = "https://example-bucket.s3.ap-northeast-2.amazonaws.com/sample-video.mp4";
        when(s3Service.getExistingFileUrl(anyString(), anyString())).thenReturn(expectedUrl);

        Video mockVideo = new Video(title, description, expectedUrl, uploader);
        when(videoRepository.save(any(Video.class))).thenReturn(mockVideo);

        // When
        VideoDto result = videoService.saveVideoWithUrl(title, description, uploader);

        // Then
        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(expectedUrl, result.getUrl());

        verify(s3Service, times(1)).getExistingFileUrl(anyString(), anyString());
        verify(videoRepository, times(1)).save(any(Video.class));
    }
}
