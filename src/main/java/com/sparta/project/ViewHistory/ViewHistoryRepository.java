package com.sparta.project.ViewHistory;

import com.sparta.project.User.User;
import com.sparta.project.Video.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {
    ViewHistory findFirstByUserAndVideoOrderByViewDateDesc(User user, Video video);
}
