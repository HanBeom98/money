package com.sparta.videoservice.repository;

import com.sparta.videoservice.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementWriteRepository extends JpaRepository<Advertisement, Long> {
    // 명령(쓰기) 작업만 처리
}
