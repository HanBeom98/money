package com.sparta.project.ViewHistory;

import com.sparta.project.User.User;
import com.sparta.project.Video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViewHistoryService {

    @Autowired
    private ViewHistoryRepository viewHistoryRepository;

    public List<ViewHistory> findAllViewHistories() {
        return viewHistoryRepository.findAll();
    }

    public ViewHistory saveViewHistory(ViewHistory viewHistory) {
        if (isAbusing(viewHistory.getUser(), viewHistory.getVideo())) {
            return null;  // 어뷰징으로 간주하고 저장하지 않음
        }
        return viewHistoryRepository.save(viewHistory);
    }

    public ViewHistory findViewHistoryById(Long id) {
        return viewHistoryRepository.findById(id).orElse(null);
    }

    private boolean isAbusing(User user, Video video) {
        ViewHistory lastHistory = findLastViewHistoryByUserAndVideo(user, video);
        if (lastHistory == null) {
            return false;
        }

        return lastHistory.getViewDate().plusSeconds(30).isAfter(LocalDateTime.now())
                && user.getLastLoginIp().equals(lastHistory.getUser().getLastLoginIp())
                && user.getLastLoginAgent().equals(lastHistory.getUser().getLastLoginAgent()); // User-Agent를 이용한 추가 방지
    }

    public ViewHistory findLastViewHistoryByUserAndVideo(User user, Video video) {
        return viewHistoryRepository.findFirstByUserAndVideoOrderByViewDateDesc(user, video);
    }

    // 비디오의 재생 시점을 업데이트
    public ViewHistory updateLastWatchedTime(ViewHistory viewHistory, Long lastWatchedTime) {
        viewHistory.setLastWatchedTime(lastWatchedTime);
        return viewHistoryRepository.save(viewHistory);
    }
}
