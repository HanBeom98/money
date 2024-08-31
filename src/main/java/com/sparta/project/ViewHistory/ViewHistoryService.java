package com.sparta.project.ViewHistory;

import com.sparta.project.Util.EntityDtoConverter;
import com.sparta.project.User.User;
import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ViewHistoryService {

    @Autowired
    private ViewHistoryRepository viewHistoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    public Flux<ViewHistoryDto> findAllViewHistories() {
        return Flux.fromIterable(viewHistoryRepository.findAll())
                .map(EntityDtoConverter::convertToDto); // EntityDtoConverter 사용
    }

    public Mono<ViewHistoryDto> saveViewHistory(ViewHistoryDto viewHistoryDto) {
        return Mono.fromSupplier(() -> {
            Video video = videoRepository.findById(viewHistoryDto.getVideoId())
                    .orElseThrow(() -> new IllegalArgumentException("Video not found"));
            User user = video.getUploader(); // DTO에서 User를 가져오는 로직으로 변경 필요
            ViewHistory viewHistory = EntityDtoConverter.convertToEntity(viewHistoryDto, video, user);
            ViewHistory savedHistory = viewHistoryRepository.save(viewHistory);
            return EntityDtoConverter.convertToDto(savedHistory); // EntityDtoConverter 사용
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
            return EntityDtoConverter.convertToDto(updatedViewHistory); // EntityDtoConverter 사용
        });
    }

    public Mono<Void> trackVideoView(Long videoId, User currentUser, Long watchTime, ServerWebExchange exchange) {
        return Mono.defer(() -> {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new IllegalArgumentException("Video not found"));

            if (currentUser.equals(video.getUploader())) {
                return Mono.empty();
            }

            String currentIp = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            LocalDateTime currentTime = LocalDateTime.now();

            ViewHistory lastView = viewHistoryRepository.findFirstByUserAndVideoOrderByViewDateDesc(currentUser, video);

            if (lastView != null && lastView.getViewDate().plusSeconds(30).isAfter(currentTime) && lastView.getIpAddress().equals(currentIp)) {
                return Mono.empty();
            }

            ViewHistory viewHistory = new ViewHistory(video, currentUser, currentTime, watchTime);
            viewHistory.setIpAddress(currentIp);
            viewHistoryRepository.save(viewHistory);

            video.setViews(video.getViews() + 1);
            videoRepository.save(video);
            return Mono.empty();
        });
    }
}
