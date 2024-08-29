package com.sparta.project.ViewHistory;

import com.sparta.project.User.User;
import com.sparta.project.User.UserRepository;
import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewHistoryService {

    @Autowired
    private ViewHistoryRepository viewHistoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    // 모든 시청 기록을 조회하는 메서드, Flux를 반환하여 여러 시청 기록을 리액티브하게 처리
    public Flux<ViewHistoryDto> findAllViewHistories() {
        return Flux.fromIterable(viewHistoryRepository.findAll())
                .map(this::entityToDto);
    }

    public Mono<ViewHistoryDto> saveViewHistory(ViewHistoryDto viewHistoryDto) {
        return Mono.fromSupplier(() -> {
            ViewHistory viewHistory = dtoToEntity(viewHistoryDto);
            ViewHistory savedHistory = viewHistoryRepository.save(viewHistory);
            return entityToDto(savedHistory);
        });
    }

    public Mono<ViewHistory> findViewHistoryById(Long id) {
        return Mono.fromSupplier(() -> viewHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ViewHistory not found")));
    }

    public Mono<ViewHistoryDto> updateLastWatchedTime(ViewHistory viewHistory, Long lastWatchedTime) {
        return Mono.fromSupplier(() -> {
            viewHistory.setLastWatchedTime(lastWatchedTime);
            ViewHistory updatedViewHistory = viewHistoryRepository.save(viewHistory);
            return entityToDto(updatedViewHistory);
        });
    }

    public Mono<Void> trackVideoView(Long videoId, User currentUser, Long watchTime, ServerWebExchange exchange) {
        return Mono.defer(() -> {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new IllegalArgumentException("Video not found"));

            // 동영상 게시자가 시청하는 경우 카운트 제외
            if (currentUser.equals(video.getUploader())) {
                System.out.println("Uploader is watching their own video. Count not incremented.");
                return Mono.empty();
            }

            String currentIp = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            LocalDateTime currentTime = LocalDateTime.now();

            ViewHistory lastView = viewHistoryRepository.findFirstByUserAndVideoOrderByViewDateDesc(currentUser, video);

            if (lastView != null && lastView.getViewDate().plusSeconds(30).isAfter(currentTime) && lastView.getIpAddress().equals(currentIp)) {
                System.out.println("Abusive behavior detected. Count not incremented.");
                return Mono.empty();
            }

            // 새로운 시청 기록 저장
            ViewHistory viewHistory = new ViewHistory(video, currentUser, currentTime, watchTime);
            viewHistory.setIpAddress(currentIp);
            viewHistoryRepository.save(viewHistory);

            // 동영상의 조회수 증가
            video.setViews(video.getViews() + 1);
            videoRepository.save(video);
            return Mono.empty();
        });
    }

    // 엔터티 -> DTO 변환 메서드
    public ViewHistoryDto entityToDto(ViewHistory viewHistory) {
        ViewHistoryDto dto = new ViewHistoryDto();
        dto.setId(viewHistory.getId());
        dto.setVideoId(viewHistory.getVideo().getId());
        dto.setUserId(viewHistory.getUser().getId());
        dto.setWatchTime(viewHistory.getWatchTime());
        dto.setLastWatchedTime(viewHistory.getLastWatchedTime());
        dto.setViewDate(viewHistory.getViewDate());
        return dto;
    }

    // DTO -> 엔터티 변환 메서드
    private ViewHistory dtoToEntity(ViewHistoryDto dto) {
        ViewHistory viewHistory = new ViewHistory();
        viewHistory.setId(dto.getId());

        // Video와 User 객체를 데이터베이스에서 가져와서 설정
        Video video = videoRepository.findById(dto.getVideoId())
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        viewHistory.setVideo(video);
        viewHistory.setUser(user);

        viewHistory.setWatchTime(dto.getWatchTime());
        viewHistory.setLastWatchedTime(dto.getLastWatchedTime());
        viewHistory.setViewDate(dto.getViewDate());
        return viewHistory;
    }
}
