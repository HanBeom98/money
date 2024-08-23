package com.sparta.project.ViewHistory;// ViewHistoryService.java

import com.sparta.project.User.User;
import com.sparta.project.User.UserRepository;
import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import com.sparta.project.ViewHistory.ViewHistory;
import com.sparta.project.ViewHistory.ViewHistoryDto;
import com.sparta.project.ViewHistory.ViewHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<ViewHistoryDto> findAllViewHistories() {
        List<ViewHistory> histories = viewHistoryRepository.findAll();
        return histories.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public ViewHistoryDto saveViewHistory(ViewHistoryDto viewHistoryDto) {
        ViewHistory viewHistory = dtoToEntity(viewHistoryDto);
        ViewHistory savedHistory = viewHistoryRepository.save(viewHistory);
        return entityToDto(savedHistory);
    }

    public ViewHistory findViewHistoryById(Long id) {
        return viewHistoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ViewHistory not found"));
    }

    public ViewHistoryDto updateLastWatchedTime(ViewHistory viewHistory, Long lastWatchedTime) {
        viewHistory.setLastWatchedTime(lastWatchedTime);
        ViewHistory updatedViewHistory = viewHistoryRepository.save(viewHistory);
        return entityToDto(updatedViewHistory);
    }

    // 엔터티 -> DTO 변환 메서드
    public ViewHistoryDto entityToDto(ViewHistory viewHistory) {  // 여기에서 private을 public으로 변경
        ViewHistoryDto dto = new ViewHistoryDto();
        dto.setId(viewHistory.getId());
        dto.setVideoId(viewHistory.getVideo().getId());
        dto.setUserId(viewHistory.getUser().getId());
        dto.setWatchTime(viewHistory.getWatchTime());
        dto.setLastWatchedTime(viewHistory.getLastWatchedTime());
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
        return viewHistory;
    }
}
