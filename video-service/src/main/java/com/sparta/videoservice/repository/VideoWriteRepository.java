package com.sparta.videoservice.repository;

import com.sparta.videoservice.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoWriteRepository extends JpaRepository<Video, Long> {
    // 명령(쓰기) 작업만 처리
}
