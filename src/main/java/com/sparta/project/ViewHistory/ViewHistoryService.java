package com.sparta.project.ViewHistory;

import com.sparta.project.User.User;
import com.sparta.project.User.UserRepository;
import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ViewHistoryService {

    @Autowired
    private ViewHistoryRepository viewHistoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request; // 현재 요청을 받아오기 위해 주입

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

    public void trackVideoView(Long videoId, User currentUser, Long watchTime) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));

        System.out.println("Video found: " + video.getTitle());

        // 동영상 게시자가 시청하는 경우 카운트 제외
        if (currentUser.equals(video.getUploader())) {
            System.out.println("Uploader is watching their own video. Count not incremented.");
            return;
        }

        String currentIp = request.getRemoteAddr(); // 요청한 사용자의 IP 주소 가져오기
        LocalDateTime currentTime = LocalDateTime.now();

        ViewHistory lastView = viewHistoryRepository.findFirstByUserAndVideoOrderByViewDateDesc(currentUser, video);

        System.out.println("Last view history: " + (lastView != null ? lastView.getViewDate() : "None"));

        if (lastView != null && lastView.getViewDate().plusSeconds(30).isAfter(currentTime) && lastView.getIpAddress().equals(currentIp)) {
            System.out.println("Abusive behavior detected. Count not incremented.");
            return;
        }

        System.out.println("Recording new view history.");

        // 새로운 시청 기록 저장
        ViewHistory viewHistory = new ViewHistory(video, currentUser, currentTime, watchTime);
        viewHistory.setIpAddress(currentIp); // IP 주소 저장
        viewHistoryRepository.save(viewHistory);

        // 동영상의 조회수 증가
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
        System.out.println("View count incremented.");
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
