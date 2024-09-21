package com.sparta.videoservice.repository;

import com.sparta.videoservice.model.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {
    Optional<ViewHistory> findByUserIdAndVideoId(Long userId, Long videoId);

    // 어뷰징 방지용 메서드 추가
    Optional<ViewHistory> findFirstByVideoIdAndUserIdAndIpAddressAndViewDateAfter(Long videoId, Long userId, String ipAddress, Date viewDate);
}
