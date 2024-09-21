package com.sparta.videoservice.repository;

import com.sparta.videoservice.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true) // 읽기 작업 최적화
public interface VideoReadRepository extends JpaRepository<Video, Long> {
    // 질의(읽기) 작업만 처리
}
