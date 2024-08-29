package com.sparta.project;

import com.sparta.project.User.User;
import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import com.sparta.project.ViewHistory.ViewHistory;
import com.sparta.project.ViewHistory.ViewHistoryRepository;
import com.sparta.project.ViewHistory.ViewHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewHistoryServiceTest {

    @Mock
    private ViewHistoryRepository viewHistoryRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ViewHistoryService viewHistoryService;

    private User testUser;
    private Video testVideo;
    private ServerWebExchange exchange;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setEmail("test@example.com");

        testVideo = new Video();
        testVideo.setTitle("Test Video");

        exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());
    }

    @Test
    void trackVideoView_shouldIncrementViewCountAfter30Seconds() {
        // Given
        String ipAddress = "192.168.1.1";
        when(request.getRemoteAddr()).thenReturn(ipAddress);

        // 비디오의 업로더를 다른 사용자로 설정
        User uploader = new User();
        uploader.setEmail("uploader@example.com");
        testVideo.setUploader(uploader);

        when(videoRepository.findById(anyLong())).thenReturn(Optional.of(testVideo));

        // 최근 시청 기록을 40초 전으로 설정
        ViewHistory recentViewHistory = new ViewHistory(testVideo, testUser, LocalDateTime.now().minusSeconds(40), 60L);
        recentViewHistory.setIpAddress(ipAddress);

        when(viewHistoryRepository.findFirstByUserAndVideoOrderByViewDateDesc(testUser, testVideo))
                .thenReturn(recentViewHistory);

        // When
        viewHistoryService.trackVideoView(1L, testUser, 60L, exchange);

        // Then
        verify(videoRepository, times(1)).save(any(Video.class));
        verify(viewHistoryRepository, times(1)).save(any(ViewHistory.class));
    }
}
